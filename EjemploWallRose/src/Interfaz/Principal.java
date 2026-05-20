package Interfaz;

public class Principal {

}

/**package interfaz;

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
import control.ControladoraWallRose;
import logica.Cliente;
import logica.Producto;

import java.awt.event.FocusAdapter;
import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal {

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

	/**
	 * Launch the application.
	 */
/**	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void cargarClientes() {
		ControladoraWallRose control = ControladoraWallRose.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
		model.setRowCount(0);
		List<Cliente> listaClientes = control.obtenerListadoClientes();
		for (Cliente cliente : listaClientes) {
			Object[] fila = new Object[] {cliente.getId(), cliente.getNombre(), cliente.getEmail()};
			model.addRow(fila);
		}
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
	private void agregarCliente() {}
	private void editarCliente() {}
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
				ControladoraWallRose control = ControladoraWallRose.getInstance();
				try {
					control.borrarCliente(idCliente);
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
	private void cargarProductos() {
		ControladoraWallRose control = ControladoraWallRose.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
		model.setRowCount(0);
		List<Producto> listaProductos = control.obtenerListadoProductos();
		for (Producto p : listaProductos) {
			Object[] fila = new Object[] {p.getCodigo(), p.getNombre(), p.getExistencias(), p.getUnidad(), p.getPrecio()};
			model.addRow(fila);			
		}		
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
			ControladoraWallRose control = ControladoraWallRose.getInstance();
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
						control.borrarProducto(codigo);
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
	private void cargarDatos() {
		try {
			ControladoraWallRose.cargarDatos();
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
			ControladoraWallRose.guardarDatos();
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
/**	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
/**	private void initialize() {
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
		btnAgregarCliente.setBounds(552, 50, 115, 28);
		panelClientes.add(btnAgregarCliente);
		
		btnEditarCliente = new JButton("Editar");
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
		btnAgregarProducto.setBounds(552, 11, 115, 28);
		panelProductos.add(btnAgregarProducto);
		
		btnEditarProducto = new JButton("Editar");
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
*/