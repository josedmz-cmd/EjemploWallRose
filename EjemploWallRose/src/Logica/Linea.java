package Logica;

import java.io.Serializable;

public class Linea implements Serializable  {
	private double cantidad;
	private Producto producto;
	
	public Linea(Producto producto, double cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public double CalcularPrecio() {
		return producto.getPrecio() * cantidad;
	}
	//Aquí empiezan los que no estaban en mi UML
	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public Producto getProducto() {
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
}
