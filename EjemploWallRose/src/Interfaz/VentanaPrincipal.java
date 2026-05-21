package Interfaz;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import Controladora.Controladora;
import Logica.Cliente;
import Logica.Producto;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import Controladora.Controladora;
import Logica.Cliente;
import Logica.Producto;
import Logica.Orden;

import java.awt.event.FocusAdapter;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaPrincipal {

	private JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel panelOrdenes;
    private JPanel panelProductos;
    private JTable tablaClientes;
    private JScrollPane scrollPane;
    private JButton btnVerCliente;
    private JButton btnAgregarCliente;
    private JButton btnEditarCliente;
    private JButton btnBorrar;
    private JPanel panelClientes;
    private JButton btnAgregarProducto;
    private JButton btnEditarProducto;
    private JButton btnBorrarProducto;
    private JTable tablaProductos;
    private JTable tablaOrdenes;
    private JScrollPane scrollPane_2;
    private JLabel LabelTotal;
    private JLabel labelTotalPendiente;

	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPrincipal window = new VentanaPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
    public void cargarClientes() {
        Controladora control = Controladora.getInstance();
        DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
        model.setRowCount(0);
        List<Cliente> listaClientes = control.ObtenerClientes();
        for (Cliente cliente : listaClientes) {
            Object[] fila = new Object[] {cliente.getID(), cliente.getNombre(), cliente.getEmail()};
            model.addRow(fila);
        }
    }
	
    public void refrescarClientes() {
        cargarClientes();
    }
    
	private void verCliente() {
		int numeroFila = tablaClientes.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(
					frame, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel)tablaClientes.getModel();
			String idCliente = (String)model.getValueAt(numeroFila, 0);
			DetalleCliente ventanaDetalleCliente = new DetalleCliente(idCliente);
			ventanaDetalleCliente.setVisible(true);
		}
	}

	private void agregarCliente() {
		AgregarEditarCliente ventana = new AgregarEditarCliente(this);
	    ventana.setVisible(true);
    }
	
	private void editarCliente() {
		int numeroFila = tablaClientes.getSelectedRow();
	    if (numeroFila == -1) {
	        JOptionPane.showMessageDialog(
	                frame, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
	    } else {
	        DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
	        String idCliente = (String) model.getValueAt(numeroFila, 0);
	        String nombreCliente = (String) model.getValueAt(numeroFila, 1);
	        String emailCliente = (String) model.getValueAt(numeroFila, 2);
	        AgregarEditarCliente ventana = new AgregarEditarCliente(this, idCliente, nombreCliente, emailCliente);
	        ventana.setVisible(true);
	    }
    }
	
	private void borrarCliente() {
		int numeroFila = tablaClientes.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(
					frame, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel)tablaClientes.getModel();
			String idCliente = (String)model.getValueAt(numeroFila, 0);
			String nombreCliente = (String)model.getValueAt(numeroFila, 1);
			int respuesta = JOptionPane.showConfirmDialog(frame, 
					"Se eliminará la información del cliente " + nombreCliente + " (id="+ idCliente +") y todas sus órdenes asociadas.",
					"Confirmar", 
					JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				Controladora control = Controladora.getInstance();
				try {
					control.BorrarCliente(idCliente);
					cargarClientes();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(
							frame, 
							"Error al borrar cliente: " + e.toString(), 
							"Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	public void cargarProductos() {
	    Controladora control = Controladora.getInstance();
	    DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
	    model.setRowCount(0);
	    List<Producto> listaProductos = control.ObtenerProductos();
	    for (Producto p : listaProductos) {
	        Object[] fila = new Object[] {p.getCodigo(), p.getNombre(), p.getExistencias(), p.getUnidad(), p.getPrecio()};
	        model.addRow(fila);            
	    }       
	}
	public void refrescarProductos() {
        cargarProductos();
    }
	private void borrarProducto() {
		int numeroFila = tablaProductos.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(
					frame, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel)tablaProductos.getModel();
			Integer codigo = (Integer)model.getValueAt(numeroFila, 0);
			String nombre = (String)model.getValueAt(numeroFila, 1);
			Double existencias = (Double)model.getValueAt(numeroFila, 2);
			String unidad = (String)model.getValueAt(numeroFila, 3);
			Double precio = (Double)model.getValueAt(numeroFila, 4);
			Controladora control = Controladora.getInstance();
			try {
				if (control.esProductoUtilizado(codigo)) {
					JOptionPane.showMessageDialog(
							frame, "El producto está siendo utilizado en una orden, no se puede eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int respuesta = JOptionPane.showConfirmDialog(frame, 
							"Se eliminará la información del producto " + nombre + " (codigo="+ codigo +").",
							"Confirmar", 
							JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						control.BorrarProducto(codigo);
						cargarProductos();
					}
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(
						frame, 
						"Error al borrar producto: " + e.toString(), 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void cargarOrdenes() {
	    Controladora control = Controladora.getInstance();
	    DefaultTableModel model = (DefaultTableModel) tablaOrdenes.getModel();
	    model.setRowCount(0);
	    List<Orden> listaOrdenes = control.ObtenerOrdenes();
	    for (Orden orden : listaOrdenes) {
	        Object[] fila = new Object[] {
	            orden.getNumero(), 
	            orden.getFechaCreacion(), 
	            orden.getEstado()
	        };
	        model.addRow(fila);
	    }
	    actualizarTotalPendiente();
	}

	public void refrescarOrdenes() {
	    cargarOrdenes();
	}

	private void actualizarTotalPendiente() {
	    Controladora control = Controladora.getInstance();
	    double total = control.obtenerMontoTotalPendiente();
	    labelTotalPendiente.setText(String.format("₡%.2f", total));
	}

	private void nuevaOrden() {
	    List<Cliente> clientes = Controladora.getInstance().ObtenerClientes();
	    if (clientes.isEmpty()) {
	        JOptionPane.showMessageDialog(frame, "No hay clientes disponibles. Debe crear un cliente primero.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    String[] nombresClientes = new String[clientes.size()];
	    for (int i = 0; i < clientes.size(); i++) {
	        nombresClientes[i] = clientes.get(i).getID() + " - " + clientes.get(i).getNombre();
	    }
	    String seleccion = (String) JOptionPane.showInputDialog(
	        frame,
	        "Seleccione el cliente:",
	        "Nueva Orden",
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        nombresClientes,
	        nombresClientes[0]
	    );
	    if (seleccion != null) {
	        String idCliente = seleccion.split(" - ")[0];
	        try {
	            Controladora control = Controladora.getInstance();
	            control.crearOrdenVacia(idCliente);
	            cargarOrdenes();
	            JOptionPane.showMessageDialog(frame, "Orden creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(frame, "Error al crear orden: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	private void verDetalleOrden() {
	    int filaSeleccionada = tablaOrdenes.getSelectedRow();
	    if (filaSeleccionada == -1) {
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar una orden.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    DefaultTableModel model = (DefaultTableModel) tablaOrdenes.getModel();
	    int numeroOrden = (int) model.getValueAt(filaSeleccionada, 0);
	    Orden orden = Controladora.getInstance().obtenerOrden(numeroOrden);
	    DetalleOrden detalle = new DetalleOrden(this, orden);
	    detalle.setVisible(true);
	}

	private void borrarOrden() {
	    int filaSeleccionada = tablaOrdenes.getSelectedRow();
	    if (filaSeleccionada == -1) {
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar una orden.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    DefaultTableModel model = (DefaultTableModel) tablaOrdenes.getModel();
	    int numeroOrden = (int) model.getValueAt(filaSeleccionada, 0);
	    String estado = (String) model.getValueAt(filaSeleccionada, 2);
	    
	    if ("Terminada".equals(estado)) {
	        JOptionPane.showMessageDialog(frame, "No se pueden borrar órdenes terminadas.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    int respuesta = JOptionPane.showConfirmDialog(frame, "¿Está seguro de borrar la orden #" + numeroOrden + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
	    if (respuesta == JOptionPane.YES_OPTION) {
	        try {
	            Controladora control = Controladora.getInstance();
	            control.borrarOrden(numeroOrden);
	            cargarOrdenes();
	            JOptionPane.showMessageDialog(frame, "Orden borrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(frame, "Error al borrar orden: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	private void cargarDatos() {
		try {
			Controladora.cargarDatos();
			cargarClientes();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					frame,
					"Error al cargar datos: " + e.toString(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	private void guardarDatos() {
		try {
			Controladora.guardarDatos();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					frame,
					"Error al guardar datos: " + e.toString(),
					"Error",
					JOptionPane.ERROR_MESSAGE);						
		}
		
	}
	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 692, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelClientes = new JPanel();
		panelClientes.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarClientes();
			}
		});
		panelClientes.addFocusListener(new FocusAdapter() {
		});
		tabbedPane.addTab("Clientes", null, panelClientes, null);
		panelClientes.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 532, 348);
		panelClientes.add(scrollPane);
		
		tablaClientes = new JTable();
		tablaClientes.addFocusListener(new FocusAdapter() {
		});
		scrollPane.setViewportView(tablaClientes);
		tablaClientes.setFillsViewportHeight(true);
		tablaClientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nombre", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnVerCliente = new JButton("Ver");
		btnVerCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verCliente();
			}
		});
		btnVerCliente.setBounds(552, 11, 115, 28);
		panelClientes.add(btnVerCliente);
		
		btnAgregarCliente = new JButton("Agregar");
		btnAgregarCliente.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        AgregarEditarCliente ventana = new AgregarEditarCliente(VentanaPrincipal.this);
		        ventana.setVisible(true);
		    }
		});
		btnAgregarCliente.setBounds(552, 50, 115, 28);
		panelClientes.add(btnAgregarCliente);

		btnEditarCliente = new JButton("Editar");
		btnEditarCliente.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int numeroFila = tablaClientes.getSelectedRow();
		        if (numeroFila == -1) {
		            JOptionPane.showMessageDialog(
		                    frame, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		        } else {
		            DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
		            String idCliente = (String) model.getValueAt(numeroFila, 0);
		            String nombreCliente = (String) model.getValueAt(numeroFila, 1);
		            String emailCliente = (String) model.getValueAt(numeroFila, 2);
		            
		            AgregarEditarCliente ventana = new AgregarEditarCliente(VentanaPrincipal.this, idCliente, nombreCliente, emailCliente);
		            ventana.setVisible(true);
		        }
		    }
		});
		btnEditarCliente.setBounds(552, 89, 115, 28);
		panelClientes.add(btnEditarCliente);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarCliente();
			}
		});
		btnBorrar.setBounds(552, 128, 115, 28);
		panelClientes.add(btnBorrar);
		
		JButton btnGuardar = new JButton("Guardar datos");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		btnGuardar.setBounds(552, 299, 115, 23);
		panelClientes.add(btnGuardar);
		
		JButton btnCargar = new JButton("Cargar datos");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatos();
			}
		});
		btnCargar.setBounds(552, 333, 115, 23);
		panelClientes.add(btnCargar);
		panelOrdenes = new JPanel();
		tabbedPane.addTab("Órdenes", null, panelOrdenes, null);
		panelOrdenes.setLayout(null);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(27, 11, 452, 336);
		panelOrdenes.add(scrollPane_2);
		
		tablaOrdenes = new JTable();
		tablaOrdenes.setFillsViewportHeight(true);
		tablaOrdenes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Fecha", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaOrdenes.getColumnModel().getColumn(0).setPreferredWidth(90);
		tablaOrdenes.getColumnModel().getColumn(1).setPreferredWidth(140);
		tablaOrdenes.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane_2.setViewportView(tablaOrdenes);
		
		JButton btnNuevaOrden = new JButton("Nueva");
		btnNuevaOrden.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        nuevaOrden();
		    }
		});
		btnNuevaOrden.setBounds(553, 14, 89, 23);
		panelOrdenes.add(btnNuevaOrden);

		JButton btnDetalle = new JButton("Detalle");
		btnDetalle.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        verDetalleOrden();
		    }
		});
		btnDetalle.setBounds(553, 62, 89, 23);
		panelOrdenes.add(btnDetalle);

		JButton btnBorrarOrden = new JButton("Borrar");
		btnBorrarOrden.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        borrarOrden();
		    }
		});
		btnBorrarOrden.setBounds(553, 114, 89, 23);
		panelOrdenes.add(btnBorrarOrden);
		
		panelOrdenes.addComponentListener(new ComponentAdapter() {
		    public void componentShown(ComponentEvent e) {
		        cargarOrdenes();
		    }
		});
		
		LabelTotal = new JLabel("Total Pendiente:");
		LabelTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		LabelTotal.setBounds(489, 171, 118, 14);
		panelOrdenes.add(LabelTotal);
		
		labelTotalPendiente = new JLabel("0.0");
		labelTotalPendiente.setBounds(492, 196, 150, 14);
		panelOrdenes.add(labelTotalPendiente);
		
		panelProductos = new JPanel();
		panelProductos.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarProductos();
			}
		});
		tabbedPane.addTab("Productos", null, panelProductos, null);
		panelProductos.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 532, 299);
		panelProductos.add(scrollPane_1);
		
		tablaProductos = new JTable();
		tablaProductos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nombre", "Existencias", "Unidad", "Precio"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Double.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(277);
		tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(77);
		tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(92);
		scrollPane_1.setViewportView(tablaProductos);
		
		btnAgregarProducto = new JButton("Agregar");
		btnAgregarProducto.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        AgregarEditarProducto ventana = new AgregarEditarProducto(VentanaPrincipal.this);
		        ventana.setVisible(true);
		    }
		});
		btnAgregarProducto.setBounds(552, 11, 115, 28);
		panelProductos.add(btnAgregarProducto);
		
		btnEditarProducto = new JButton("Editar");
		btnEditarProducto.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int numeroFila = tablaProductos.getSelectedRow();
		        if (numeroFila == -1) {
		            JOptionPane.showMessageDialog(
		                    frame, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
		        } else {
		            DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
		            int codigo = (int) model.getValueAt(numeroFila, 0);
		            Producto producto = Controladora.getInstance().ObtenerProducto(codigo);
		            
		            AgregarEditarProducto ventana = new AgregarEditarProducto(VentanaPrincipal.this, producto);
		            ventana.setVisible(true);
		        }
		    }
		});
		btnEditarProducto.setBounds(552, 50, 115, 28);
		panelProductos.add(btnEditarProducto);
		
		btnBorrarProducto = new JButton("Borrar");
		btnBorrarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarProducto();
			}
		});
		btnBorrarProducto.setBounds(552, 89, 115, 28);
		panelProductos.add(btnBorrarProducto);
	}
}
