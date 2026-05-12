package Controladora;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

import Logica.Cliente;
import Logica.Orden;
import Logica.Producto;

public class Controladora {
	private static Controladora instance = null;
	private Map<String, Cliente> clientes;
	private Map<Integer, Orden> ordenes;
	private Map<Integer, Producto> productos;
	private int consecutivoOrden;
	private int consecutivoProducto;
	
	private Controladora() {
		clientes = new TreeMap<>();
		ordenes = new TreeMap<>();
		productos = new TreeMap<>();
		consecutivoOrden = 1;
		consecutivoProducto = 1;
	}
	
	public static Controladora getInstance(){ //Originalmente no en mi UML
		if (instance == null) {
			instance = new Controladora();
		}
		return instance;
	}
	
	public void CrearCliente(String id, String nombre, String email) throws Exception {
		if (!clientes.containsKey(id)) {
			Cliente cliente = new Cliente(id, nombre, email);
			clientes.put(id, cliente);
		} else {
			throw new Exception("Ya existe un cliente con ID: " + id);
		}
	}
	
	public void CrearOrden(String idCliente) throws Exception {
		Cliente cliente = ObtenerCliente(idCliente);
		if (cliente != null) {
			Orden orden = new Orden(cliente);
			ordenes.put(orden.getCodigo(), orden);
			cliente.agregarOrden(orden);
		} else {
			throw new Exception("Cliente no encontrado: " + idCliente);
		}
	}
	
	public void crearOrdenVacia(String idCliente) throws Exception { //Originalmente no en mi UML
		CrearOrden(idCliente);
	}
	
	public void CrearProducto(String nombre, double existencias, String unidad, double precio) {
		Producto producto = new Producto(nombre, existencias, unidad, precio);
		productos.put(producto.getCodigo(), producto);
	}
	
	public List ObtenerClientes() { 
		return new ArrayList<>(clientes.values());
	}
	
	public Cliente ObtenerCliente(String id) { //Originalmente no en mi UML
		return clientes.get(id);
	}
	
	public List ObtenerListaOrdenesClientes() { //Originalmente no en mi UML
		List<Orden> todasOrdenes = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			todasOrdenes.addAll(cliente.ObtenerOrdenes());
		}
		return todasOrdenes;
	}
	
	public List obtenerListadoOrdenesIniciadasCliente() { //Originalmente no en mi UML
		List<Orden> ordenesIniciadas = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			ordenesIniciadas.addAll(cliente.ObtOrdenesIn());
		}
		return ordenesIniciadas;
	}
	public List obtenerListadoOrdenesPendientesCliente() { //Originalmente no en mi UML
		List<Orden> ordenesPendientes = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			ordenesPendientes.addAll(cliente.ObtOrdenesPen());
		}
		return ordenesPendientes;
}
	public List obtenerListadoOrdenesTerminadasCliente() { //Originalmente no en mi UML
		List<Orden> ordenesTerminadas = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			ordenesTerminadas.addAll(cliente.ObtOrdenesTer());
		}
		return ordenesTerminadas;
}

	public void BorrarCliente(String id) throws Exception {
		Cliente cliente = clientes.remove(id);
		if (cliente != null) {
			// Borrar todas las órdenes del cliente
			List<Orden> ordenesCliente = cliente.ObtenerOrdenes();
			for (Orden orden : ordenesCliente) {
				ordenes.remove(orden.getCodigo());
			}
		} else {
			throw new Exception("Cliente no encontrado: " + id);
		}
	}
	
	public void ActualizarCliente(String id, String nombre, String email) throws Exception {
		Cliente cliente = clientes.get(id);
		if (cliente != null) {
			cliente.setNombre(nombre);
			cliente.setEmail(email);
		} else {
			throw new Exception("Cliente no encontrado: " + id);
		}
	}
	
	public List ObtenerOrdenes() { //ObtenerOrdenes
		return new ArrayList<>(ordenes.values());
	}
	
	public Orden obtenerOrden(int numeroOrden) { //Originalmente no en mi UML
		return ordenes.get(numeroOrden);
	}
	
	public List obtenerLineasOrden(int numeroOrden) { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		if (orden != null) {
			return orden.obtLineas();
		}
		return new ArrayList<>();
	}
	
	public void establecerOrdenPendiente(int numeroOrden) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		if (orden != null) {
			orden.setEstado("Pendiente");
		} else {
			throw new Exception("Orden no encontrada: " + numeroOrden);
		}
	}
	
	public void establecerOrdenTerminada(int numeroOrden) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		if (orden != null) {
			orden.setEstado("Terminada");
		} else {
			throw new Exception("Orden no encontrada: " + numeroOrden);
		}
	}
	
	public void agregarLineaOrden(int numeroOrden, int codigoProducto, double cantidad) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		Producto producto = productos.get(codigoProducto);
		if (orden != null && producto != null) {
			orden.agregarLinea(producto, cantidad);
		} else {
			if (orden == null) {
				throw new Exception("Orden no encontrada: " + numeroOrden);
			}
			if (producto == null) {
				throw new Exception("Producto no encontrado: " + codigoProducto);
			}
		}
	}
	
	public void actualizarLineaOrden(int numeroOrden, int numeroLinea, int codigoProducto, double cantidad) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		Producto producto = productos.get(codigoProducto);
		
		if (orden != null && producto != null) {
			orden.actualizarLinea(numeroLinea, producto, cantidad);
		} else {
			if (orden == null) {
				throw new Exception("Orden no encontrada: " + numeroOrden);
			}
			if (producto == null) {
				throw new Exception("Producto no encontrado: " + codigoProducto);
			}
		}
	}
	
	public void borrarLineaOrden(int numeroOrden, int numeroLinea) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		if (orden != null) {
			orden.borrarLinea(numeroLinea);
		} else {
			throw new Exception("Orden no encontrada: " + numeroOrden);
		}
	}
	
	public void borrarOrden(int numeroOrden) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.remove(numeroOrden);
		if (orden != null) {
			Cliente cliente = orden.getCliente();
			if (cliente != null) {
				cliente.borrarOrden(orden);
			}
		} else {
			throw new Exception("Orden no encontrada: " + numeroOrden);
		}
	}
	
	public List ObtenerProductos() {
		return new ArrayList<>(productos.values());
	}
	
	public Producto ObtenerProducto(int codigoProducto) { //Originalmente no en mi UML
		return productos.get(codigoProducto);
	}
	
	public void actualizarProducto(int codigoProducto, String nombre, double existencias, String unidad, double precio) throws Exception { //Originalmente no en mi UML
		Producto producto = productos.get(codigoProducto);
		if (producto != null) {
			producto.ActualizarPro(nombre, existencias, unidad, precio);
		} else {
			throw new Exception("Producto no encontrado: " + codigoProducto);
		}
	}

	public void BorrarProducto(int codigo) throws Exception { //Originalmente no en mi UML
		Producto producto = productos.remove(codigo);
		if (producto == null) {
			throw new Exception("Producto no encontrado: " + codigo);
		}
	}
	
	public double obtenerMontoTotalPendiente() { //Originalmente no en mi UML
		double total = 0;
		List<Orden> ordenesPendientes = obtenerListadoOrdenesPendientesCliente();
		for (Orden orden : ordenesPendientes) {
			total += orden.obtMontoTotal();
		}
		return total;
	}
}
