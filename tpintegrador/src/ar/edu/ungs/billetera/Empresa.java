package ar.edu.ungs.billetera;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Empresa {

	private String cuit;
	private String nombreFantasia;
	private String telefono;
	private String email;
	private String nombreContacto;

	// DNI autorizados
	private Set<String> personasAutorizadas; // usamos el dni como identificador

	// cuentas corporativas indexadas por CVU
	private Map<String, CuentaCorporativa> cuentasCorporativas; // la clave seria el cvu de la cuenta coorporativa

	public Empresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto) {

		if (cuit == null || cuit.isEmpty()) { // verifica que no este vacia o no contenga espacios vacios
			throw new IllegalArgumentException("CUIT invalido");
		}

		if (nombreFantasia == null || nombreFantasia.isEmpty()) {
			throw new IllegalArgumentException("Nombre fantasia invalido");
		}

		if (telefono == null || telefono.isEmpty()) {
			throw new IllegalArgumentException("Telefono invalido");
		}

		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email invalido");
		}

		if (nombreContacto == null || nombreContacto.isEmpty()) {
			throw new IllegalArgumentException("Nombre contacto invalido");
		}

		this.cuit = cuit;
		this.nombreFantasia = nombreFantasia;
		this.telefono = telefono;
		this.email = email;
		this.nombreContacto = nombreContacto;

		this.personasAutorizadas = new HashSet<>();
		this.cuentasCorporativas = new HashMap<>();
	}

	public void agregarPersonaAutorizada(String dni) {

		if (dni == null || dni.isBlank()) {
			throw new IllegalArgumentException("DNI invalido");
		}

		if (personasAutorizadas.contains(dni)) {
			throw new IllegalArgumentException("La persona ya esta autorizada");
		}

		personasAutorizadas.add(dni);
	}

	public boolean estaAutorizado(String dni) {

		if (dni == null) {
			return false;
		}

		return personasAutorizadas.contains(dni);
	}

	public Set<String> obtenerAutorizados() {
		return new HashSet<>(personasAutorizadas);
	}

	public void agregarCuentaCorporativa(CuentaCorporativa cuenta) {
		if (cuenta == null) {
			throw new IllegalArgumentException("Cuenta invalida");
		}

		cuentasCorporativas.put(cuenta.getCvu(), cuenta);
	}

	public CuentaCorporativa obtenerCuenta(String cvu) {
		return cuentasCorporativas.get(cvu);
	}

	public Collection<CuentaCorporativa> obtenerCuentasCorporativas() {
		return cuentasCorporativas.values();
	}

	public String getCuit() {
		return cuit;
	}
	
	public String getNomFantasia() {
		return nombreFantasia;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getEmail() {
		return email;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

}
