package Logica;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
	private String id;
	private String nombre;
	private String email;
	private List<Orden> ordenes;
	
	public Cliente() {
		
	}
	
	public List ObtenerOrdenes() {
		return ordenes;
	}
	
	public List ObtOrdenesIn(){
		//Obtener ordenes inicializadas
		return ordenes;
	}
	public List ObtOrdenesPen() {
		//Obtener ordenes pendientes
		return ordenes;
	}
	
	public List ObtOrdenesTer() {
		//Obtener ordenes terminadas
		return ordenes;
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
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	private boolean esEmailValido(String email) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
