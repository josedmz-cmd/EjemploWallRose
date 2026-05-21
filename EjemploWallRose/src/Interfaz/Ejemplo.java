package Interfaz;

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

public class Ejemplo {

	private JFrame frame;
	private JLabel labelMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejemplo window = new Ejemplo();
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
	public Ejemplo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
