package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {

	private String dni; // antes lo llamabamos "id" pero el interfaz usa "dni"
	private String nombre;
	private String telefono;
	private String email;
	private Map<String, Cuenta> cuentas; // alias : String
	private double totalInvertido;

	public Usuario(String dni, String nombre, String telefono, String email) {
		if (dni == null || dni.isEmpty() || nombre == null || nombre.isEmpty() || telefono == null || telefono.isEmpty()
				|| email == null || email.isEmpty()) {
			throw new RuntimeException("DNI, nombre, teléfono y email son obligatorios.");
		}
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.cuentas = new HashMap<>();
		this.totalInvertido = 0;
	}

	public void agregarCuenta(Cuenta c) {
		if (c == null) {
			throw new RuntimeException("No se puede agregar una cuenta nula al usuario.");
		}

		if (this.cuentas.containsKey(c.getAlias())) {
			throw new RuntimeException(
					"La cuenta con alias " + c.getAlias() + " ya se encuentra vinculada a este usuario.");
		}

		cuentas.put(c.getAlias(), c);
	}

	public List<Cuenta> listarCuentas() {
		return new ArrayList<>(this.cuentas.values());
	}

	public void actualizarTotalInvertido(double monto) {
		totalInvertido += monto;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Usuario [DNI: ");
		sb.append(getDni());
		sb.append(", Nombre: ");
		sb.append(getNombre()).append("]\n");

		sb.append("  - Contacto: ");
		sb.append(getTelefono());
		sb.append(" / ");
		sb.append(getEmail());
		sb.append("\n");

		sb.append("  - Total Invertido: $");
		sb.append(getTotalInvertido());
		sb.append("\n");

		sb.append("  - Cuentas vinculadas: ");
		sb.append(cuentas.values().toString());

		return sb.toString();
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	public double getTotalInvertido() {
		return totalInvertido;
	}

	public Map<String, Cuenta> getCuentas() {
		return cuentas;
	}
}
