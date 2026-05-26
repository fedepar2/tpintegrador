package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {

	private String cvu; // cvu de 22 digitos...
	private String alias;
	private Usuario titular; // para el toString()
	private double saldo;
	private List<Actividad> actividades;
	private int cantidadOperaciones;
	private double montoInvertido; // para que punto 9 sea O(1)

	public Cuenta(String alias, double depositoInicial, Usuario titular) { // elimine el atributo cvu por que ya que lo
																			// crea utilitarios
		if (alias == null || alias.trim().isEmpty()) { // separamos validaciones para incluir formato
			throw new RuntimeException("Alias invalido.");
		}
		if (titular == null) {
			throw new RuntimeException("Titular invalido.");
		}

		if (depositoInicial < 0) {
			throw new RuntimeException("El depósito inicial no puede ser negativo.");
		}
		this.cvu = Utilitarios.generarSiguienteCvu(); // utilitarios genera los cvus
		this.alias = alias;
		this.saldo = depositoInicial;
		this.titular = titular;
		this.actividades = new ArrayList<>();
		this.cantidadOperaciones = 0;
		this.montoInvertido = 0; // por claridad
	}

	protected abstract void validarOperacion(double monto); // así hay override en subclases

	public void transferir(Cuenta destino, double monto) {

		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}

		if (this.getSaldo() < monto) {
			throw new IllegalStateException("Saldo insuficiente.");
		}

		destino.validarOperacion(monto);

		saldo -= monto;
		destino.acreditar(monto);
	}

	public void registrarActividad(Actividad act) {
		if (act != null) {
			actividades.add(act);
			if (act.getAprobada()) {
				cantidadOperaciones++;
			}
		}
		// añadir excepción?
	}

	public double saldoTotal() { // deposito más el invertido
		return getSaldo() + getMontoInvertido();
	}

	public void invertir(Inversion inv) {

		validarOperacion(inv.getMonto());

		saldo -= inv.getMonto();

		montoInvertido += inv.getMonto();

		titular.actualizarTotalInvertido(inv.getMonto());
	}

	@Override
	public String toString() { // o debería ser abstracto?
		StringBuilder sb = new StringBuilder();

		sb.append(getClass().getSimpleName().replace("Cuenta", "")); // tipo de cuenta concreta
		sb.append(": ");
		sb.append(getAlias());
		sb.append(" (");
		sb.append(getCvu());
		sb.append(")");

		return sb.toString();
	}

	public String getCvu() {
		return cvu;
	}

	public String getAlias() {
		return alias;
	}

	public double getSaldo() {
		return saldo;
	}

	public Usuario getTitular() {
		return titular;
	}

	public List<Actividad> getActividades() { // reemplace el metodo para no dejar expuesto al objeto actividades
		return new ArrayList<>(actividades);
	}

	public int getCantOperaciones() {
		return cantidadOperaciones;
	}

	public double getMontoInvertido() {
		return montoInvertido;
	}

	public void acreditar(double monto) {
		if(monto <= 0) {
			throw new RuntimeException("El monto a acreditar debe ser positivo.");
		}
		saldo += monto;
	}

}
