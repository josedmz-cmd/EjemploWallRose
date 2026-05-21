package Controladora;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Logica.Cliente;
import Logica.Orden;
import Logica.Producto;
import Logica.Linea;

public class Controladora implements Serializable {
	private static Controladora instance = null;
    private Integer consecutivoOrden;
    private Integer consecutivoProducto;
    private Map<String, Cliente> clientes;
    private Map<Integer, Orden> ordenes;
    private Map<Integer, Producto> productos;
	
	
	private Controladora() {
		this.consecutivoOrden = 1;
        this.consecutivoProducto = 1;
		clientes = new TreeMap<>();
		ordenes = new TreeMap<>();
		productos = new TreeMap<>();
		try {
			this.CrearCliente("1-1111-1111", "Samantha Carter", "samantha@sg1.com");
			this.CrearCliente("2-2222-2222", "Daniel Jackson", "daniel@sg1.com");
			this.CrearCliente("3-3333-3333", "Jack O´Niel", "jack@sg1.com");
			this.CrearProducto("Arroz", 20.0, "kg", 900.0);
			this.CrearProducto("Frijoles", 10.0, "kg", 1000.0);
			this.CrearProducto("Harina", 12.5, "kg", 1080.0);
			this.crearOrdenVacia("1-1111-1111");
			this.agregarLineaOrden(1, 1, 1.0);
			this.agregarLineaOrden(1, 2, 1.0);
			this.agregarLineaOrden(1, 3, 1.0);
			this.crearOrdenVacia("1-1111-1111");
			this.agregarLineaOrden(2, 1, 2.0);
			this.agregarLineaOrden(2, 2, 2.0);
			this.agregarLineaOrden(2, 3, 2.0);
			this.establecerOrdenPendiente(2);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static Controladora getInstance(){ //Originalmente no en mi UML
		if (instance == null) {
			instance = new Controladora();
		}
		return instance;
	}
	//Validacion
	private void verificarClienteExistente(String idCliente) throws Exception { //Originalmente no en mi UML
		if (!clientes.containsKey(idCliente))
			throw new Exception("Cliente no encontrado");
	}
	
	private void verificarClienteNoExistente(String idCliente) throws Exception {
        if (clientes.containsKey(idCliente))
            throw new Exception("Ya existe un cliente con ID: " + idCliente);
    }
	
	private void verificarLineaOrdenExistente(int numeroOrden, int numeroLinea) throws Exception { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		if (orden == null) {
	        throw new Exception("Orden no encontrada");
	    }
		if (numeroLinea < 0 || numeroLinea > orden.cantidadLineas())
			throw new Exception("Número de línea no valido");
	}
	
    
    private void verificarProductoExistente(Integer codigoProducto) throws Exception {
        if (!productos.containsKey(codigoProducto))
            throw new Exception("Producto no encontrado: " + codigoProducto);
    }
    
    private void verificarOrdenExistente(Integer numeroOrden) throws Exception {
        if (!ordenes.containsKey(numeroOrden))
            throw new Exception("Orden no encontrada: " + numeroOrden);
    }
    
    private boolean esEmailValido(String email) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    private void verificarEmail(String email) throws Exception {
        if (!esEmailValido(email))
            throw new Exception("Email inválido: " + email);
    }
    
    //Clientes
	public void CrearCliente(String id, String nombre, String email) throws Exception {
		if (!clientes.containsKey(id)) {
			Cliente cliente = new Cliente(id, nombre, email);
			clientes.put(id, cliente);
		} else {
			throw new Exception("Ya existe un cliente con ID: " + id);
		}
	}
	
	public List<Cliente> ObtenerClientes() { 
		return new ArrayList<>(clientes.values());
	}
	
	public Cliente ObtenerCliente(String id) throws Exception { //Originalmente no en mi UML
		verificarClienteExistente(id);
		return clientes.get(id);
	}
	
	public void BorrarCliente(String id) throws Exception {
		verificarClienteExistente(id);
        Cliente cliente = clientes.remove(id);
        if (cliente != null) {
            Map<Integer, Orden> ordenesCliente = cliente.getOrdenes();
            for (Integer numeroOrden : ordenesCliente.keySet()) {
                ordenes.remove(numeroOrden);
            }
        }
	}
	
	public void ActualizarCliente(String id, String nombre, String email) throws Exception {
		verificarClienteExistente(id);
        verificarEmail(email);
        Cliente cliente = clientes.get(id);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
	}
	
	//Productos
	public void CrearProducto(String nombre, double existencias, String unidad, double precio) throws Exception {
		if (nombre.equals(""))
            throw new Exception("Nombre no puede ser vacío.");
        if (existencias < 0 || precio < 0)
            throw new Exception("El precio y existencias no pueden ser negativos.");
		for (Producto p : productos.values()) {
	        if (p.getNombre().equals(nombre)) {
	            throw new Exception("Ya existe un producto con nombre: " + nombre);
	        }
	    }
		Producto producto = new Producto(nombre, existencias, unidad, precio, consecutivoProducto);
		productos.put(consecutivoProducto, producto);
        consecutivoProducto++;
	}
	
	public List<Producto> ObtenerProductos() {
		return new ArrayList<>(productos.values());
	}
	
	public Producto ObtenerProducto(int codigoProducto) { //Originalmente no en mi UML
		return productos.get(codigoProducto);
	}
	
	public void actualizarProducto(int codigoProducto, String nombre, double existencias, String unidad, double precio) throws Exception { //Originalmente no en mi UML
		verificarProductoExistente(codigoProducto);
        Producto producto = productos.get(codigoProducto);
        producto.ActualizarPro(nombre, existencias, unidad, precio);
	}

	public void BorrarProducto(int codigo) throws Exception { //Originalmente no en mi UML
		verificarProductoExistente(codigo);
        for (Orden orden : ordenes.values()) {
            for (Linea linea : orden.obtLineas()) {
                Producto producto = linea.getProducto();
                if (producto.getCodigo() == codigo) {
                    throw new Exception("El producto está siendo utilizado en la orden " + orden.getNumero() + ".");
                }
            }
        }
        productos.remove(codigo);
	}
	
	public boolean esProductoUtilizado(int codigoProducto) throws Exception {
        verificarProductoExistente(codigoProducto);
        for (Orden orden : ordenes.values()) {
            for (Linea linea : orden.obtLineas()) {
                if (linea.getProducto().getCodigo() == codigoProducto)
                    return true;
            }
        }
        return false;
    }
	
	//Orden
	public void crearOrdenVacia(String idCliente) throws Exception { //Originalmente no en mi UML
		verificarClienteExistente(idCliente);
        Cliente cliente = clientes.get(idCliente);
        Orden orden = new Orden(cliente, consecutivoOrden);
        ordenes.put(consecutivoOrden, orden);
        cliente.agregarOrden(orden);
        consecutivoOrden++;
	}

	public List<Orden> ObtenerListaOrdenesCliente(String id) throws Exception { //Originalmente no en mi UML
		verificarClienteExistente(id);
        Cliente c = clientes.get(id);
        return c.ObtenerOrdenes();
	}
	
	private List<Orden> ObtenerListaOrdenesClienteEstado(String id, String estado) throws Exception { //Originalmente no en mi UML
		verificarClienteExistente(id);
		Cliente c = clientes.get(id);
		List<Orden> listaOrdenes = new ArrayList<Orden>();
		Map<Integer, Orden> ordenes = c.getOrdenes();
		for (Orden o : ordenes.values()) {
			if (o.getEstado().equals(estado))
				listaOrdenes.add(o);
		}
		return listaOrdenes;
	}
	
	public List<Orden> ObtenerListaOrdenesClientes() { //Originalmente no en mi UML
		List<Orden> todasOrdenes = new ArrayList<Orden>();
		for (Cliente cliente : clientes.values()) {
			todasOrdenes.addAll(cliente.ObtenerOrdenes());
		}
		return todasOrdenes;
	}
	
	public List<Orden> obtenerListadoOrdenesIniciadasCliente() { //Originalmente no en mi UML
		List<Orden> ordenesIniciadas = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			ordenesIniciadas.addAll(cliente.ObtOrdenesIn());
		}
		return ordenesIniciadas;
	}
	
	public List<Orden> ObtenerListaOrdenesClienteIniciadas(String id) throws Exception{ //Originalmente no en mi UML
		return ObtenerListaOrdenesClienteEstado(id, "Iniciado");
	}
	
	public List<Orden> obtenerListadoOrdenesPendientesCliente() { //Originalmente no en mi UML
		List<Orden> ordenesPendientes = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			ordenesPendientes.addAll(cliente.ObtOrdenesPen());
		}
		return ordenesPendientes;
	}
	
	public List<Orden> ObtenerListaOrdenesClientePendientes(String id) throws Exception{ //Originalmente no en mi UML
		return ObtenerListaOrdenesClienteEstado(id, "Pendiente");
	}
	
	public List<Orden> obtenerListadoOrdenesTerminadasCliente() { //Originalmente no en mi UML
		List<Orden> ordenesTerminadas = new ArrayList<>();
		for (Cliente cliente : clientes.values()) {
			ordenesTerminadas.addAll(cliente.ObtOrdenesTer());
		}
		return ordenesTerminadas;
}
	
	public List<Orden> ObtenerListaOrdenesClienteTerminadas(String id) throws Exception{ //Originalmente no en mi UML
		return ObtenerListaOrdenesClienteEstado(id, "Terminada");
	}

	public List<Orden> ObtenerOrdenes() { 
		return new ArrayList<>(ordenes.values());
	}
	
	public Orden obtenerOrden(int numeroOrden) { //Originalmente no en mi UML
		return ordenes.get(numeroOrden);
	}
	
	public List<Linea> obtenerLineasOrden(int numeroOrden) { //Originalmente no en mi UML
		Orden orden = ordenes.get(numeroOrden);
		if (orden != null) {
			return orden.obtLineas();
		}
		return new ArrayList<>();
	}
	
	public void establecerOrdenPendiente(int numeroOrden) throws Exception { //Originalmente no en mi UML
		verificarOrdenExistente(numeroOrden);
        Orden orden = ordenes.get(numeroOrden);
        orden.setEstado("Pendiente");
	}
	
	public void establecerOrdenTerminada(int numeroOrden) throws Exception { //Originalmente no en mi UML
		verificarOrdenExistente(numeroOrden);
        Orden orden = ordenes.get(numeroOrden);
        orden.setEstado("Terminada");
	}
	
	public void agregarLineaOrden(int numeroOrden, int codigoProducto, double cantidad) throws Exception { //Originalmente no en mi UML
		verificarOrdenExistente(numeroOrden);
        verificarProductoExistente(codigoProducto);
        if (cantidad < 0)
            throw new Exception("La cantidad no debe ser negativa.");
        Orden orden = ordenes.get(numeroOrden);
        Producto producto = productos.get(codigoProducto);
        orden.agregarLinea(producto, cantidad);
	}
	
	public void actualizarLineaOrden(int numeroOrden, int numeroLinea, int codigoProducto, double cantidad) throws Exception { //Originalmente no en mi UML
		verificarOrdenExistente(numeroOrden);
        verificarLineaOrdenExistente(numeroOrden, numeroLinea);
        verificarProductoExistente(codigoProducto);
        Orden orden = ordenes.get(numeroOrden);
        Producto producto = productos.get(codigoProducto);
        orden.actualizarLinea(numeroLinea, producto, cantidad);
	}
	
	public void borrarLineaOrden(int numeroOrden, int numeroLinea) throws Exception { //Originalmente no en mi UML
		verificarOrdenExistente(numeroOrden);
        verificarLineaOrdenExistente(numeroOrden, numeroLinea);
        Orden orden = ordenes.get(numeroOrden);
        orden.borrarLinea(numeroLinea);
	}
	
	public void borrarOrden(int numeroOrden) throws Exception { //Originalmente no en mi UML
		verificarOrdenExistente(numeroOrden);
        Orden orden = ordenes.remove(numeroOrden);
        if (orden != null) {
            Cliente cliente = orden.getCliente();
            if (cliente != null) {
                cliente.borrarOrden(orden);
            }
        }
	}
	
	public double obtenerMontoTotalPendiente() { //Originalmente no en mi UML
		double total = 0.0;
		List<Orden> ordenesPendientes = obtenerListadoOrdenesPendientesCliente();
		for (Orden orden : ordenesPendientes) {
			if ("Pendiente".equals(orden.getEstado())) {
                total += orden.obtMontoTotal();
            }
		}
		return total;
	}
	
	public double obtenerMontoTotalPendienteCliente(String idCliente) throws Exception {
	    verificarClienteExistente(idCliente);
	    double total = 0.0;
	    List<Orden> ordenesPendientes = ObtenerListaOrdenesClienteEstado(idCliente, "Pendiente");
	    for (Orden orden : ordenesPendientes) {
	        total += orden.obtMontoTotal();
	    }
	    return total;
	}
	
	public static void guardarDatos() throws IOException { //Originalmente no en mi UML
        FileOutputStream file = new FileOutputStream("Datos.dat");
        ObjectOutputStream stream = new ObjectOutputStream(file);
        stream.writeObject(instance);
        stream.close();
        file.close();
    }
    
    public static void cargarDatos() throws IOException, ClassNotFoundException { //Originalmente no en mi UML
        FileInputStream file = new FileInputStream("Datos.dat");
        ObjectInputStream stream = new ObjectInputStream(file);
        instance = (Controladora) stream.readObject();
        stream.close();
        file.close();
    }
}
