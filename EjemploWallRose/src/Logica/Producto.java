package Logica;

public class Producto {
	private int codigo;
	private String nombre;
	private double existencias;
	private String unidad;
	private double precio;
	private static int consecutivo = 1;
	
	public Producto(String nombre, double existencias, String unidad, double precio) {
		codigo = consecutivo++;
		this.nombre = nombre;
		this.existencias = existencias;
		this.unidad = unidad;
		this.precio = precio;
	}
	public void ActualizarPro(String nombre, double existencias, String unidad, double precio) {
		this.nombre = nombre;
		this.existencias = existencias;
		this.unidad = unidad;
		this.precio = precio;
	}
	//Aquí empiezan los que no estaban en mi UML
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public double getExistencias() {
		return existencias;
	}
	public void setExistencias(double existencias) {
		this.existencias = existencias;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
