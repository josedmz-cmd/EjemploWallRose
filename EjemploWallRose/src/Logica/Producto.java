package Logica;

public class Producto {
	private int codigo;
	private String nombre;
	private float existencias;
	private String unidad;
	private float precio;
	private static int consecutivo;
	
	public Producto(String nombre, float existencias, String unidad, float precio) {
		codigo = consecutivo++;
		this.nombre = nombre;
		this.existencias = existencias;
		this.unidad = unidad;
		this.precio = precio;
	}
	public void ActualizarPro(String nombre, float existencias, String unidad, float precio) {
		this.nombre = nombre;
		this.existencias = existencias;
		this.unidad = unidad;
		this.precio = precio;
	}
}
