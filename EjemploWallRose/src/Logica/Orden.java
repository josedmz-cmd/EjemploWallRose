package Logica;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Orden {
	private int codigo;
	private int numero;
	private LocalDateTime fechaCreacion;
	private	String estado; //Iniciado, pendiente o terminada
	private float impuesto = 0.13f;
	private List<Linea> Lineas;
	private Cliente cliente;
	private static int contadorOrdenes = 1;
	
	public Orden (Cliente cliente) {
		this.codigo = contadorOrdenes++;
		this.numero = codigo;
		this.fechaCreacion = LocalDateTime.now();
		this.estado = "Iniciado";
		this.Lineas = new ArrayList<>();
		this.cliente = cliente;
	}
	
	public double calcularMonto() { //No en mi UML
		
	}
	
	public double calcularMontoImpuesto() { //No en mi UML
		
	}
	
	public double obtMontoTotal() {
		
	}
	
	public List obtLineas() {
		return Lineas;
	}
	
	public void actualizar(int numero) {
		
	}
	
	public void agregarLinea(int codigoPro, int cantidad) {
		
	}
	
	public void actualizarLinea(int numeroLinea, int codigoPro, int cantidad) {
		
	}
	
	public void borrarLinea(int numeroLinea) {
		
	}
	//Aquí empiezan los que no estaban en mi UML

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getNumero() {
		return numero;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
}
