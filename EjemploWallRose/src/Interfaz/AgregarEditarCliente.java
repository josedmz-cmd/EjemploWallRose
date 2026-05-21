package Interfaz;

import java.awt.EventQueue;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Controladora.Controladora;

public class AgregarEditarCliente {

	private JFrame frame;
    private JTextField textFieldID;
    private JTextField textFieldNombre;
    private JTextField textFieldEmail;
    private JButton BtnGuardar;
    private boolean esEdicion;
    private String idOriginal;
    private VentanaPrincipal ventanaPrincipal;

	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	AgregarEditarCliente window = new AgregarEditarCliente();
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
    public AgregarEditarCliente() {
        this.ventanaPrincipal = null;
        this.esEdicion = false;
        this.idOriginal = null;
        initialize();
        frame.setTitle("Agregar Cliente");
    }
	/**
	 * Create the application. Agregar
	 */
    public AgregarEditarCliente(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.esEdicion = false;
        this.idOriginal = null;
        initialize();
        frame.setTitle("Agregar Cliente");
    }
    /**
	 * Create the application. Agregar
	 */
    public AgregarEditarCliente(VentanaPrincipal ventanaPrincipal, String id, String nombre, String email) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.esEdicion = true;
        this.idOriginal = id;
        initialize();
        frame.setTitle("Editar Cliente");
        textFieldID.setText(id);
        textFieldID.setEditable(false);
        textFieldNombre.setText(nombre);
        textFieldEmail.setText(email);
    }
	/**
	 * Initialize the contents of the frame.
	 */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel LabelTitulo = new JLabel(esEdicion ? "Editar Cliente" : "Nuevo Cliente");
        LabelTitulo.setFont(new Font("Tahoma", Font.PLAIN, 24));
        LabelTitulo.setBounds(25, 11, 364, 40);
        frame.getContentPane().add(LabelTitulo);
        
        JLabel LabelID = new JLabel("ID:");
        LabelID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelID.setBounds(25, 73, 44, 20);
        frame.getContentPane().add(LabelID);
        
        textFieldID = new JTextField();
        textFieldID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldID.setBounds(79, 75, 310, 20);
        frame.getContentPane().add(textFieldID);
        textFieldID.setColumns(10);
        
        JLabel LabelNombre = new JLabel("Nombre:");
        LabelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelNombre.setBounds(10, 116, 73, 20);
        frame.getContentPane().add(LabelNombre);
        
        textFieldNombre = new JTextField();
        textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldNombre.setBounds(79, 118, 310, 20);
        frame.getContentPane().add(textFieldNombre);
        textFieldNombre.setColumns(10);
        
        JLabel LabelEmail = new JLabel("Email:");
        LabelEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LabelEmail.setBounds(23, 165, 46, 14);
        frame.getContentPane().add(LabelEmail);
        
        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldEmail.setBounds(79, 164, 310, 20);
        frame.getContentPane().add(textFieldEmail);
        textFieldEmail.setColumns(10);
        
        BtnGuardar = new JButton("Guardar");
        BtnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });
        BtnGuardar.setBounds(335, 227, 89, 23);
        frame.getContentPane().add(BtnGuardar);
        
        JButton BtnCancelar = new JButton("Cancelar");
        BtnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        BtnCancelar.setBounds(236, 227, 89, 23);
        frame.getContentPane().add(BtnCancelar);
    }
    
    private void guardarCliente() {
        String id = textFieldID.getText().trim();
        String nombre = textFieldNombre.getText().trim();
        String email = textFieldEmail.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "El ID es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "El email es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Controladora control = Controladora.getInstance();
        try {
            if (esEdicion) {
                control.ActualizarCliente(idOriginal, nombre, email);
                JOptionPane.showMessageDialog(frame, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                control.CrearCliente(id, nombre, email);
                JOptionPane.showMessageDialog(frame, "Cliente creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            if (ventanaPrincipal != null) {
                ventanaPrincipal.refrescarClientes();
            }
            frame.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}