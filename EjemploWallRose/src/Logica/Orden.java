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
	
	public Orden (Cliente cliente, int codigo) {
		this.codigo = codigo;
		this.numero = codigo;
		this.fechaCreacion = LocalDateTime.now();
		this.estado = "Iniciado";
		this.Lineas = new ArrayList<>();
		this.cliente = cliente;
	}
	
	public double calcularMonto() { //No en mi UML
		double total = 0;
		for (Linea linea : Lineas) {
			total += linea.CalcularPrecio();
		}
		return total;
	}
	
	public double calcularMontoImpuesto() { //No en mi UML
		return calcularMonto() * impuesto;
	}
	
	public double obtMontoTotal() {
		return calcularMonto() + calcularMontoImpuesto();
	}
	
	public List obtLineas() {
		return Lineas;
	}
	
	public void actualizar(int numero) {
		this.numero = numero;
	}
	
	public void agregarLinea(Producto producto, double cantidad) {
		if (producto.getExistencias() >= cantidad) {
			Linea linea = new Linea(producto, cantidad);
			Lineas.add(linea);
			producto.setExistencias(producto.getExistencias() - cantidad);
		} else {
			System.out.println("No hay suficientes existencias del producto: " + producto.getNombre());
		}
	}
	
	public void actualizarLinea(int posicion, Producto producto, double cantidad) {
		if (posicion >= 0 && posicion < Lineas.size()) {
			Linea lineaActual = Lineas.get(posicion);
			Producto productoAnterior = lineaActual.getProducto();
			productoAnterior.setExistencias(productoAnterior.getExistencias() + lineaActual.getCantidad());
			if (producto.getExistencias() >= cantidad) {
				Linea nuevaLinea = new Linea(producto, cantidad);
				Lineas.set(posicion, nuevaLinea);
				producto.setExistencias(producto.getExistencias() - cantidad);
			} else {
				System.out.println("No hay suficientes existencias del nuevo producto");
			}
		}
	}
	
	public void borrarLinea(int numeroLinea) {
		if (numeroLinea >= 0 && numeroLinea < Lineas.size()) {
			Linea lineaEliminar = Lineas.get(numeroLinea);
			Producto producto = lineaEliminar.getProducto();
			producto.setExistencias(producto.getExistencias() + lineaEliminar.getCantidad());
			Lineas.remove(numeroLinea);
		}
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
	
	public int getCodigo() {
		return codigo;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public int cantidadLineas() {
		return Lineas.size();
	}
	
	public Linea getLinea(int numeroLinea) {
		if (numeroLinea >= 0 && numeroLinea < Lineas.size()) {
	        return Lineas.get(numeroLinea);
	    }
	    return null;
	}
}
