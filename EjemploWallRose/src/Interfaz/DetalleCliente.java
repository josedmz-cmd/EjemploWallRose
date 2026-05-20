package Interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Controladora.Controladora;
import Logica.Cliente;
import Logica.Orden;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DetalleCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tablaOrdenes;
	private String idCliente;
	private JLabel labelTotalPendiente;
	private JLabel labelIDCliente;
	private JLabel labelNombreCliente;
	private JLabel labelEmailCliente;

	/**
	 * Launch the application.
	 */
	private void cargarDatosCliente() {
		Controladora control = Controladora.getInstance();
		try {
			Cliente c = control.ObtenerCliente(idCliente);
			labelIDCliente.setText(c.getID());
			labelNombreCliente.setText(c.getNombre());
			labelEmailCliente.setText(c.getEmail());
			labelTotalPendiente.setText(c.calcularMontoPendiente().toString());
			cargarOrdenesCliente();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					this, 
					"Error al cargar datos del cliente: " + e.toString(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	private void cargarOrdenesCliente() {
		Controladora control = Controladora.getInstance();
		try {
			DefaultTableModel model = (DefaultTableModel) tablaOrdenes.getModel();
			model.setRowCount(0);
			List<Orden> listaOrdenes = control.obtenerListadoOrdenesCliente(idCliente);
			for (Orden o : listaOrdenes) {
				Object[] fila = new Object[] {o.getNumero(), o.getFecha(), o.getEstado().name()};
				model.addRow(fila);			
			}			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					this, 
					"Error al cargar las órdenes del cliente: " + e.toString(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Create the dialog.
	 */
	public DetalleCliente(String idCliente) {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		this.idCliente = idCliente;
		setTitle("Detalle cliente");
		setBounds(100, 100, 409, 312);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarDatosCliente();
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPanel.add(lblNewLabel);
		
		labelIDCliente = new JLabel("");
		labelIDCliente.setBounds(67, 11, 301, 14);
		contentPanel.add(labelIDCliente);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(10, 36, 59, 14);
		contentPanel.add(lblNewLabel_1);
		
		labelNombreCliente = new JLabel("");
		labelNombreCliente.setBounds(67, 36, 301, 14);
		contentPanel.add(labelNombreCliente);
		
		JLabel lblNewLabel_2 = new JLabel("Email:");
		lblNewLabel_2.setBounds(10, 61, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		labelEmailCliente = new JLabel("");
		labelEmailCliente.setBounds(67, 61, 301, 14);
		contentPanel.add(labelEmailCliente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 108, 259, 127);
		contentPanel.add(scrollPane);
		
		tablaOrdenes = new JTable();
		tablaOrdenes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Fecha", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaOrdenes.getColumnModel().getColumn(1).setPreferredWidth(94);
		tablaOrdenes.getColumnModel().getColumn(2).setPreferredWidth(91);
		scrollPane.setViewportView(tablaOrdenes);
		
		JLabel lblNewLabel_3 = new JLabel("Órdenes del cliente:");
		lblNewLabel_3.setBounds(10, 86, 160, 14);
		contentPanel.add(lblNewLabel_3);
		
		JButton btnVerTodas = new JButton("Todas");
		btnVerTodas.setBounds(279, 111, 104, 23);
		contentPanel.add(btnVerTodas);
		
		JButton btnVerIniciadas = new JButton("Iniciadas");
		btnVerIniciadas.setBounds(279, 145, 104, 23);
		contentPanel.add(btnVerIniciadas);
		
		JButton btnVerPendientes = new JButton("Pendientes");
		btnVerPendientes.setBounds(279, 179, 104, 23);
		contentPanel.add(btnVerPendientes);
		
		JButton btnVerTerminadas = new JButton("Terminadas");
		btnVerTerminadas.setBounds(279, 213, 104, 23);
		contentPanel.add(btnVerTerminadas);
		
		JLabel lblNewLabel_4 = new JLabel("Total pendiente:");
		lblNewLabel_4.setBounds(10, 246, 117, 14);
		contentPanel.add(lblNewLabel_4);
		
		labelTotalPendiente = new JLabel("");
		labelTotalPendiente.setBounds(137, 246, 133, 14);
		contentPanel.add(labelTotalPendiente);
		cargarDatosCliente();
	}
}