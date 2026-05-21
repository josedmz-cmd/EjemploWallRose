package Interfaz;

import java.awt.EventQueue;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import Controladora.Controladora;
import Logica.Orden;
import Logica.Linea;
import Logica.Producto;
import Logica.Cliente;

public class DetalleOrden {

	private JFrame frame;
    private JTable table;
    private Orden orden;
    private VentanaPrincipal ventanaPrincipal;
    private JLabel LabelIDCliente;
    private JLabel LabelNombreCliente;
    private JLabel LabelNumeroOrden;
    private JLabel LabelEstadoOrden;
    private JLabel LabelFechaActual;
    private JLabel LabelCostoOrden;
    private JLabel LabelImpuestoOrden;
    private JLabel LabelTotalOrden;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnBorrar;
    private JButton btnPendiente;
    private JButton btnTerminar;

	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DetalleOrden window = new DetalleOrden(null, null);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	/**
	 * Create the application.
	 */
    public DetalleOrden(VentanaPrincipal ventanaPrincipal, Orden orden) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.orden = orden;
        initialize();
        if (orden != null) {
            cargarDatosOrden();
            actualizarBotonesSegunEstado();
        }
    }
    
    private void actualizarBotonesSegunEstado() {
        String estado = orden.getEstado();
        boolean esIniciado = "Iniciado".equals(estado);
        boolean esPendiente = "Pendiente".equals(estado);
        boolean esTerminada = "Terminada".equals(estado);
        btnAgregar.setEnabled(esIniciado);
        btnEditar.setEnabled(esIniciado);
        btnBorrar.setEnabled(esIniciado);
        btnPendiente.setEnabled(esIniciado);
        btnTerminar.setEnabled(esPendiente);
        if (esTerminada) {
            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnBorrar.setEnabled(false);
            btnPendiente.setEnabled(false);
            btnTerminar.setEnabled(false);
        }
    }
    
    private void cargarDatosOrden() {
        Cliente cliente = orden.getCliente();
        LabelIDCliente.setText(cliente.getID());
        LabelNombreCliente.setText(cliente.getNombre());
        LabelNumeroOrden.setText(String.valueOf(orden.getNumero()));
        LabelEstadoOrden.setText(orden.getEstado());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LabelFechaActual.setText(orden.getFechaCreacion().format(formatter));
        cargarLineasOrden();
        actualizarTotales();
    }
    
    private void cargarLineasOrden() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<Linea> lineas = orden.obtLineas();
        for (Linea linea : lineas) {
            Producto producto = linea.getProducto();
            Object[] fila = new Object[] {
                producto.getCodigo(),
                producto.getNombre(),
                linea.getCantidad(),
                linea.CalcularPrecio()
            };
            model.addRow(fila);
        }
    }
    
    private void actualizarTotales() {
        double costo = orden.calcularMonto();
        double impuesto = orden.calcularMontoImpuesto();
        double total = orden.obtMontoTotal();
        LabelCostoOrden.setText(String.format("₡%.2f", costo));
        LabelImpuestoOrden.setText(String.format("₡%.2f", impuesto));
        LabelTotalOrden.setText(String.format("₡%.2f", total));
    }
    
    private void agregarLinea() {
        Controladora control = Controladora.getInstance();
        List<Producto> productos = control.ObtenerProductos();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay productos disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] nombresProductos = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            nombresProductos[i] = productos.get(i).getCodigo() + " - " + productos.get(i).getNombre() + " (Stock: " + productos.get(i).getExistencias() + ")";
        }
        String seleccion = (String) JOptionPane.showInputDialog(
            frame,
            "Seleccione un producto:",
            "Agregar Producto",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nombresProductos,
            nombresProductos[0]
        );
        if (seleccion != null) {
            int codigoProducto = Integer.parseInt(seleccion.split(" - ")[0]);
            Producto producto = control.ObtenerProducto(codigoProducto);
            String cantidadStr = JOptionPane.showInputDialog(frame, "Ingrese la cantidad:", "Cantidad", JOptionPane.QUESTION_MESSAGE);
            if (cantidadStr != null) {
                try {
                    double cantidad = Double.parseDouble(cantidadStr);
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(frame, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    control.agregarLineaOrden(orden.getNumero(), codigoProducto, cantidad);
                    cargarLineasOrden();
                    actualizarTotales();
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error al agregar línea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void editarLinea() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(frame, "Debe seleccionar una línea.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int codigoProducto = (int) model.getValueAt(filaSeleccionada, 0);
        double cantidadActual = (double) model.getValueAt(filaSeleccionada, 2);
        String cantidadStr = JOptionPane.showInputDialog(frame, "Ingrese la nueva cantidad:", cantidadActual);
        if (cantidadStr != null) {
            try {
                double nuevaCantidad = Double.parseDouble(cantidadStr);
                if (nuevaCantidad <= 0) {
                    JOptionPane.showMessageDialog(frame, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }  
                Controladora control = Controladora.getInstance();
                control.actualizarLineaOrden(orden.getNumero(), filaSeleccionada, codigoProducto, nuevaCantidad);
                cargarLineasOrden();
                actualizarTotales();     
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error al editar línea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void borrarLinea() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(frame, "Debe seleccionar una línea.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(frame, "¿Está seguro de eliminar esta línea?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                Controladora control = Controladora.getInstance();
                control.borrarLineaOrden(orden.getNumero(), filaSeleccionada);
                cargarLineasOrden();
                actualizarTotales();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error al borrar línea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void establecerPendiente() {
    	if (orden.obtLineas().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No se puede marcar como pendiente una orden sin líneas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Controladora control = Controladora.getInstance();
            control.establecerOrdenPendiente(orden.getNumero());
            orden = control.obtenerOrden(orden.getNumero());
            LabelEstadoOrden.setText(orden.getEstado());
            actualizarBotonesSegunEstado();
            if (ventanaPrincipal != null) {
                ventanaPrincipal.refrescarOrdenes();
                ventanaPrincipal.cargarProductos();
            }
            JOptionPane.showMessageDialog(frame, "Orden marcada como Pendiente. Las existencias han sido actualizadas.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage() + "\n\nNo se restó ningún producto. La orden permanece iniciada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void establecerTerminada() {
        try {
            Controladora control = Controladora.getInstance();
            control.establecerOrdenTerminada(orden.getNumero());
            orden = control.obtenerOrden(orden.getNumero());
            LabelEstadoOrden.setText(orden.getEstado());
            actualizarBotonesSegunEstado();
            JOptionPane.showMessageDialog(frame, "Orden marcada como Terminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
	/**
	 * Initialize the contents of the frame.
	 */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 650, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblDetalleOrdenDe = new JLabel("Detalle Orden de Compra");
        lblDetalleOrdenDe.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDetalleOrdenDe.setBounds(10, 11, 236, 30);
        frame.getContentPane().add(lblDetalleOrdenDe);
        
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(10, 52, 60, 14);
        frame.getContentPane().add(lblCliente);
        
        LabelIDCliente = new JLabel("");
        LabelIDCliente.setBounds(70, 52, 100, 14);
        frame.getContentPane().add(LabelIDCliente);
        
        LabelNombreCliente = new JLabel("");
        LabelNombreCliente.setBounds(170, 52, 300, 14);
        frame.getContentPane().add(LabelNombreCliente);
        
        JLabel LabelNumero = new JLabel("Numero Orden:");
        LabelNumero.setBounds(10, 76, 100, 14);
        frame.getContentPane().add(LabelNumero);
        
        LabelNumeroOrden = new JLabel("");
        LabelNumeroOrden.setBounds(110, 76, 80, 14);
        frame.getContentPane().add(LabelNumeroOrden);
        
        JLabel LabelEstado = new JLabel("Estado:");
        LabelEstado.setBounds(200, 76, 50, 14);
        frame.getContentPane().add(LabelEstado);
        
        LabelEstadoOrden = new JLabel("");
        LabelEstadoOrden.setBounds(250, 76, 100, 14);
        frame.getContentPane().add(LabelEstadoOrden);
        
        JLabel LabelFecha = new JLabel("Fecha:");
        LabelFecha.setBounds(400, 11, 46, 14);
        frame.getContentPane().add(LabelFecha);
        
        LabelFechaActual = new JLabel("");
        LabelFechaActual.setBounds(446, 11, 150, 14);
        frame.getContentPane().add(LabelFechaActual);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 101, 500, 250);
        frame.getContentPane().add(scrollPane);
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Código", "Nombre Producto", "Cantidad", "Costo"}
        ) {
            Class[] columnTypes = new Class[] {Integer.class, String.class, Double.class, Double.class};
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        scrollPane.setViewportView(table);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarLinea();
            }
        });
        btnAgregar.setBounds(520, 101, 100, 25);
        frame.getContentPane().add(btnAgregar);
        
        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarLinea();
            }
        });
        btnEditar.setBounds(520, 136, 100, 25);
        frame.getContentPane().add(btnEditar);
        
        btnBorrar = new JButton("Borrar");
        btnBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrarLinea();
            }
        });
        btnBorrar.setBounds(520, 171, 100, 25);
        frame.getContentPane().add(btnBorrar);
        
        JLabel LabelCosto = new JLabel("Costo:");
        LabelCosto.setBounds(250, 370, 60, 14);
        frame.getContentPane().add(LabelCosto);
        
        JLabel LabelImpuesto = new JLabel("Impuesto (13%):");
        LabelImpuesto.setBounds(220, 395, 100, 14);
        frame.getContentPane().add(LabelImpuesto);
        
        JLabel LabelTotal = new JLabel("Total:");
        LabelTotal.setBounds(260, 420, 60, 14);
        frame.getContentPane().add(LabelTotal);
        
        LabelCostoOrden = new JLabel("₡0.00");
        LabelCostoOrden.setBounds(320, 370, 120, 14);
        frame.getContentPane().add(LabelCostoOrden);
        
        LabelImpuestoOrden = new JLabel("₡0.00");
        LabelImpuestoOrden.setBounds(320, 395, 120, 14);
        frame.getContentPane().add(LabelImpuestoOrden);
        
        LabelTotalOrden = new JLabel("₡0.00");
        LabelTotalOrden.setBounds(320, 420, 120, 14);
        frame.getContentPane().add(LabelTotalOrden);
        
        btnPendiente = new JButton("Pendiente");
        btnPendiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                establecerPendiente();
            }
        });
        btnPendiente.setBounds(450, 370, 100, 25);
        frame.getContentPane().add(btnPendiente);
        
        btnTerminar = new JButton("Terminar");
        btnTerminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                establecerTerminada();
            }
        });
        btnTerminar.setBounds(450, 405, 100, 25);
        frame.getContentPane().add(btnTerminar);
    }
}