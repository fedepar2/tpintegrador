package ar.edu.ungs.billetera;

import java.util.ArrayList;

public abstract class Cuenta {

	private String cvu;
	private String alias;
	private Usuario titular; // para que el toString() de actividades sesa O(1); creo que no es necesario
								// igual
	private double saldo;
	private ArrayList<Actividad> actividades;
	private int cantidadOperaciones;

	public Cuenta(String cvu, String alias, double depositoInicial, Usuario titular) {
		this.cvu = cvu;
		this.alias = alias;
		this.saldo = depositoInicial;
		this.titular = titular;
		this.actividades = new ArrayList<>();
		this.cantidadOperaciones = 0;
	}

	protected abstract void validarOperacion(double monto); // así hay override en subclases

	public void transferir(Cuenta destino, double monto) {

	}

	public void invertir(Inversion inv) {

	}

	public void registrarActividad(Actividad act) {

	}

	public String getCvu() {
		return cvu;
	}

	public double saldoTotal() {
		return 0;
	}
}
