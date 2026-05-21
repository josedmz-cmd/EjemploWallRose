package Interfaz;

import java.awt.EventQueue;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Controladora.Controladora;
import Logica.Producto;

public class AgregarEditarProducto {

	private JFrame frame;
    private JTextField textFieldNombre;
    private JTextField textFieldExistencias;
    private JTextField textFieldPrecio;
    private JComboBox<String> comboBoxUnidad;
    private JLabel labelCodigoProducto;
    private boolean esEdicion;
    private int codigoOriginal;
    private VentanaPrincipal ventanaPrincipal;

	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AgregarEditarProducto window = new AgregarEditarProducto();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	/**
	 * Create the application.
	 */
    public AgregarEditarProducto() {
        this.ventanaPrincipal = null;
        this.esEdicion = false;
        this.codigoOriginal = -1;
        initialize();
        frame.setTitle("Agregar Producto");
    }
    /**
	 * Create the application. Agregar
	 */
    public AgregarEditarProducto(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.esEdicion = false;
        this.codigoOriginal = -1;
        initialize();
        frame.setTitle("Agregar Producto");
        labelCodigoProducto.setText("(Se asignará automáticamente)");
    }
    /**
	 * Create the application. Editar
	 */
    public AgregarEditarProducto(VentanaPrincipal ventanaPrincipal, Producto producto) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.esEdicion = true;
        this.codigoOriginal = producto.getCodigo();
        initialize();
        frame.setTitle("Editar Producto");
        labelCodigoProducto.setText(String.valueOf(producto.getCodigo()));
        textFieldNombre.setText(producto.getNombre());
        textFieldExistencias.setText(String.valueOf(producto.getExistencias()));
        textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
        String unidad = producto.getUnidad();
        switch (unidad.toLowerCase()) {
            case "kg":
                comboBoxUnidad.setSelectedIndex(0);
                break;
            case "l":
                comboBoxUnidad.setSelectedIndex(1);
                break;
            case "m":
                comboBoxUnidad.setSelectedIndex(2);
                break;
            case "cm":
                comboBoxUnidad.setSelectedIndex(3);
                break;
            case "unidades":
                comboBoxUnidad.setSelectedIndex(4);
                break;
            default:
                comboBoxUnidad.setSelectedIndex(0);
                break;
        }
    }
	/**
	 * Initialize the contents of the frame.
	 */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel LabelTitulo = new JLabel("Detalle Producto");
        LabelTitulo.setFont(new Font("Tahoma", Font.PLAIN, 24));
        LabelTitulo.setBounds(10, 11, 197, 30);
        frame.getContentPane().add(LabelTitulo);
        
        JLabel LabelCodigo = new JLabel("Código:");
        LabelCodigo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelCodigo.setBounds(10, 52, 56, 20);
        frame.getContentPane().add(LabelCodigo);
        
        labelCodigoProducto = new JLabel("");
        labelCodigoProducto.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelCodigoProducto.setBounds(76, 52, 301, 19);
        frame.getContentPane().add(labelCodigoProducto);
        
        JLabel LabelNombre = new JLabel("Nombre:");
        LabelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelNombre.setBounds(10, 92, 68, 20);
        frame.getContentPane().add(LabelNombre);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldNombre.setBounds(76, 88, 301, 30);
        frame.getContentPane().add(textFieldNombre);
        textFieldNombre.setColumns(10);
        
        JLabel LabelExistencias = new JLabel("Existencias:");
        LabelExistencias.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelExistencias.setBounds(10, 127, 86, 20);
        frame.getContentPane().add(LabelExistencias);
        
        textFieldExistencias = new JTextField();
        textFieldExistencias.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldExistencias.setBounds(105, 123, 272, 30);
        frame.getContentPane().add(textFieldExistencias);
        textFieldExistencias.setColumns(10);
        
        JLabel LabelUnidad = new JLabel("Unidad:");
        LabelUnidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelUnidad.setBounds(10, 170, 68, 19);
        frame.getContentPane().add(LabelUnidad);
        
        comboBoxUnidad = new JComboBox<>();
        comboBoxUnidad.setModel(new DefaultComboBoxModel<>(new String[] {"Kg", "L", "M", "Cm", "Unidades"}));
        comboBoxUnidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxUnidad.setBounds(106, 164, 101, 30);
        frame.getContentPane().add(comboBoxUnidad);
        
        JLabel LabelPrecio = new JLabel("Precio:");
        LabelPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelPrecio.setBounds(10, 212, 68, 30);
        frame.getContentPane().add(LabelPrecio);
        
        textFieldPrecio = new JTextField();
        textFieldPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldPrecio.setBounds(76, 213, 301, 30);
        frame.getContentPane().add(textFieldPrecio);
        textFieldPrecio.setColumns(10);
        
        JButton BtnGuardar = new JButton("Guardar");
        BtnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        BtnGuardar.setBounds(335, 270, 89, 23);
        frame.getContentPane().add(BtnGuardar);
        
        JButton BtnCancelar = new JButton("Cancelar");
        BtnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        BtnCancelar.setBounds(236, 270, 89, 23);
        frame.getContentPane().add(BtnCancelar);
    }
    
    private void guardarProducto() {
        String nombre = textFieldNombre.getText().trim();
        String existenciasStr = textFieldExistencias.getText().trim();
        String precioStr = textFieldPrecio.getText().trim();
        String unidad = (String) comboBoxUnidad.getSelectedItem();
        
        // Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double existencias;
        double precio;
        
        try {
            existencias = Double.parseDouble(existenciasStr);
            if (existencias < 0) {
                JOptionPane.showMessageDialog(frame, "Las existencias no pueden ser negativas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Las existencias deben ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                JOptionPane.showMessageDialog(frame, "El precio no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Controladora control = Controladora.getInstance();
        
        try {
            if (esEdicion) {
                // Actualizar producto existente
                control.actualizarProducto(codigoOriginal, nombre, existencias, unidad, precio);
                JOptionPane.showMessageDialog(frame, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Crear nuevo producto
                control.CrearProducto(nombre, existencias, unidad, precio);
                JOptionPane.showMessageDialog(frame, "Producto creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Refrescar la tabla de productos en la ventana principal
            if (ventanaPrincipal != null) {
                ventanaPrincipal.refrescarProductos();
            }
            
            // Cerrar la ventana
            frame.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}