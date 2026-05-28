package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta implements Comparable<Cuenta>{

	private String cvu;
	private String alias;
	private Usuario titular;
	private double saldo;
	private List<Actividad> actividades;
	private int cantidadOperaciones;
	private double montoInvertido;

	public Cuenta(String alias, double depositoInicial, Usuario titular) {

		if (alias == null || alias.isEmpty()) {
			throw new IllegalArgumentException("Alias invalido.");
		}
		if (titular == null) {
			throw new IllegalArgumentException("Titular invalido.");
		}

		if (depositoInicial < 0) {
			throw new IllegalArgumentException("El depósito inicial no puede ser negativo.");
		}
		this.cvu = Utilitarios.generarSiguienteCvu(); // utilitarios genera los cvus
		this.alias = alias;
		this.saldo = depositoInicial;
		this.titular = titular;
		this.actividades = new ArrayList<>();
		this.cantidadOperaciones = 0;
		this.montoInvertido = 0;
	}

	protected abstract void validarInversion(double monto);

	protected abstract void validarTransferencia(double monto);

	public void transferir(Cuenta destino, double monto) {

		validarTransferencia(monto);
		destino.validarTransferencia(monto);

		saldo -= monto;
		destino.acreditar(monto);
	}

	public void registrarActividad(Actividad act) {
		if (act == null) {
			throw new IllegalArgumentException("La actividad debe existir.");
		}
		else {
			actividades.add(act);
			if (act.getAprobada()) {
				cantidadOperaciones++;
			}
		}
	}

	public double saldoTotal() { // deposito más el invertido
		return getSaldo() + getMontoInvertido();
	}

	public void invertir(Inversion inv) {

		validarInversion(inv.getMonto());

		saldo -= inv.getMonto();

		montoInvertido += inv.getMonto();

		titular.actualizarTotalInvertido(inv.getMonto());
	}

	@Override
	public String toString() {
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

	public List<Actividad> getActividades() { // para no dejar expuesto al objeto actividades
		return new ArrayList<>(actividades);
	}

	public int getCantOperaciones() {
		return cantidadOperaciones;
	}

	public double getMontoInvertido() {
		return montoInvertido;
	}

	public void acreditar(double monto) {
		if (monto <= 0) {
			throw new IllegalArgumentException("El monto a acreditar debe ser positivo.");
		}
		saldo += monto;
	}
	
	@Override
    public int compareTo(Cuenta otra) {

        int misOper = this.getCantOperaciones();
        int otrasOper = otra.getCantOperaciones();

        return Integer.compare(otrasOper, misOper); //el orden importa
    }

}
