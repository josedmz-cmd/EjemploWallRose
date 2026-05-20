package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal {

	private JFrame frame;

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
		frame.setBounds(100, 100, 563, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panelPrincipal = new JPanel();
		tabbedPane.addTab("Principal", null, panelPrincipal, null);
		
		JPanel panelClientes = new JPanel();
		tabbedPane.addTab("Clientes", null, panelClientes, null);
		panelClientes.setLayout(null);
		
		JButton BotonVisualizar_C = new JButton("Ver");
		BotonVisualizar_C.setBounds(443, 11, 89, 23);
		panelClientes.add(BotonVisualizar_C);
		
		JButton BotonAgregar_C = new JButton("Agregar");
		BotonAgregar_C.setBounds(443, 56, 89, 23);
		panelClientes.add(BotonAgregar_C);
		
		JButton BotonEditar_C = new JButton("Editar");
		BotonEditar_C.setBounds(443, 111, 89, 23);
		panelClientes.add(BotonEditar_C);
		
		JButton BotonBorrar_C = new JButton("Borrar");
		BotonBorrar_C.setBounds(443, 165, 89, 23);
		panelClientes.add(BotonBorrar_C);
		
		JPanel panelOrdenes = new JPanel();
		tabbedPane.addTab("Ordenes", null, panelOrdenes, null);
		panelOrdenes.setLayout(null);
		
		JButton BotonAgregar_O = new JButton("Nueva");
		BotonAgregar_O.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		BotonAgregar_O.setBounds(453, 0, 89, 23);
		panelOrdenes.add(BotonAgregar_O);
		
		JButton BotonVisualizar_O = new JButton("Detalle");
		BotonVisualizar_O.setBounds(453, 55, 89, 23);
		panelOrdenes.add(BotonVisualizar_O);
		
		JButton BotonBorrar_O = new JButton("Borrar");
		BotonBorrar_O.setBounds(453, 102, 89, 23);
		panelOrdenes.add(BotonBorrar_O);
		
		JPanel panelProductos = new JPanel();
		tabbedPane.addTab("Productos", null, panelProductos, null);
		panelProductos.setLayout(null);
		
		JButton BotonAgregar_P = new JButton("Agregar");
		BotonAgregar_P.setBounds(443, 11, 89, 23);
		panelProductos.add(BotonAgregar_P);
		
		JButton BotonEditar_P = new JButton("Editar");
		BotonEditar_P.setBounds(443, 63, 89, 23);
		panelProductos.add(BotonEditar_P);
		
		JButton BotonBorrar_P = new JButton("Borrar");
		BotonBorrar_P.setBounds(443, 125, 89, 23);
		panelProductos.add(BotonBorrar_P);
	}
}
/**
package pruebaInterfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal {

	private JFrame frame;
	private JLabel labelMensaje;

	/**
	 * Launch the application.
	 */
/**	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
/**	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
/**	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 622, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelClientes = new JPanel();
		tabbedPane.addTab("Clientes", null, panelClientes, null);
		panelClientes.setLayout(null);
		
		JButton botonActualizar = new JButton("Actualizar");
		botonActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarMensaje();
			}
		});
		botonActualizar.setBounds(24, 51, 107, 23);
		panelClientes.add(botonActualizar);
		
		labelMensaje = new JLabel("Hola");
		labelMensaje.setBounds(24, 26, 107, 14);
		panelClientes.add(labelMensaje);
		
		JPanel panelOrdenes = new JPanel();
		tabbedPane.addTab("Órdenes", null, panelOrdenes, null);
		
		JPanel panelProductos = new JPanel();
		tabbedPane.addTab("Productos", null, panelProductos, null);
	}
	
	private void actualizarMensaje() {
		labelMensaje.setText("Adiós");
	}
}

*/