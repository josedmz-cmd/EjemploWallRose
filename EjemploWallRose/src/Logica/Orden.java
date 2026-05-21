package Logica;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Orden implements Serializable {
	private int codigo;
	private int numero;
	private LocalDateTime fechaCreacion;
	private	String estado; //Iniciado, pendiente o terminada
	private float impuesto = 0.13f;
	private List<Linea> Lineas;
	private Cliente cliente;
	
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
	
	public List<Linea> obtLineas() {
		return Lineas;
	}
	
	public void actualizar(int numero) {
		this.numero = numero;
	}
	
	public void agregarLinea(Producto producto, double cantidad) {
		Linea linea = new Linea(producto, cantidad);
        Lineas.add(linea);
	}
	
	public void actualizarLinea(int posicion, Producto producto, double cantidad) {
		if (posicion >= 0 && posicion < Lineas.size()) {
            Linea lineaActual = Lineas.get(posicion);
            lineaActual.setProducto(producto);
            lineaActual.setCantidad(cantidad);
        }
    }
	
	public void borrarLinea(int numeroLinea) {
        if (numeroLinea >= 0 && numeroLinea < Lineas.size()) {
            Lineas.remove(numeroLinea);
        }
    }
	//Aquí empiezan los que no estaban en mi UML

	public boolean validarExistencias() {
	    for (Linea linea : Lineas) {
	        Producto producto = linea.getProducto();
	        double cantidad = linea.getCantidad();
	        if (producto.getExistencias() < cantidad) {
	            return false;
	        }
	    }
	    return true;
	}

	public void restarExistencias() throws Exception {
		for (Linea linea : Lineas) {
            Producto producto = linea.getProducto();
            double cantidad = linea.getCantidad();
            if (producto.getExistencias() < cantidad) {
                throw new Exception("No hay suficientes existencias de: " + producto.getNombre() + 
                                    " (Stock: " + producto.getExistencias() + ", Solicitado: " + cantidad + ")");
            }
        }
		for (Linea linea : Lineas) {
            Producto producto = linea.getProducto();
            double cantidad = linea.getCantidad();
            producto.setExistencias(producto.getExistencias() - cantidad);
        }
    }
	
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
