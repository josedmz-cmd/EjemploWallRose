package Logica;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Serializable;

public class Cliente implements Serializable {
	private String id;
	private String nombre;
	private String email;
	private Map<Integer, Orden> ordenes;
	
	public Cliente(String id, String nombre, String email) throws Exception {
		this.id = id;
		this.nombre = nombre;
		setEmail(email);
		this.ordenes = new TreeMap<>();
	}
	
	public void agregarOrden(Orden orden) { //No en mi UML
		ordenes.put(orden.getCodigo(), orden);
		orden.setCliente(this);
	}
	
	public void borrarOrden(Orden orden) { //No en mi UML
		ordenes.remove(orden.getCodigo());
	}
	
	public Map<Integer, Orden> getOrdenes() {
		return ordenes;
	}
	
	public List<Orden> ObtenerOrdenes(){
		return new ArrayList<>(ordenes.values());
	}
	
	public List<Orden> ObtOrdenesIn(){ //Obtener ordenes inicializadas
		List<Orden> ordenesIniciadas = new ArrayList<>();
		for (Orden orden : ordenes.values()) {
			if ("Iniciado".equals(orden.getEstado())) {
				ordenesIniciadas.add(orden);
			}
		}
		return ordenesIniciadas;
	}
	public List<Orden> ObtOrdenesPen() { //Obtener ordenes pendientes
		List<Orden> ordenesPendientes = new ArrayList<>();
		for (Orden orden : ordenes.values()) {
			if ("Pendiente".equals(orden.getEstado())) {
				ordenesPendientes.add(orden);
			}
		}
		return ordenesPendientes;
	}
	
	public List<Orden> ObtOrdenesTer() {//Obtener ordenes terminadas
		List<Orden> ordenesTerminadas = new ArrayList<>();
		for (Orden orden : ordenes.values()) {
			if ("Terminada".equals(orden.getEstado())) {
				ordenesTerminadas.add(orden);
			}
		}
		return ordenesTerminadas;
	}
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) throws Exception {
		if (esEmailValido(email)) {
			this.email = email;
		} else {
			throw new Exception("Email inválido: " + email);
		}
	}
	
	private boolean esEmailValido(String email) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
