package ar.edu.ungs.billetera;

import java.util.ArrayList;

public abstract class Cuenta {

	private String cvu;
	private String alias;
	private Usuario titular; //para que el toString()
	private double saldo;
	private ArrayList<Actividad> actividades;
	private int cantidadOperaciones;
	private double montoInvertido; //para que punto 9 sea O(1)

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
		validarOperacion(inv.getMonto());
		saldo -= inv.getMonto();
		montoInvertido += inv.getMonto();
		registrarActividad(inv);
	}

	public void registrarActividad(Actividad act) {
		if (act != null && act.getAprobada()) {
			actividades.add(act);
			cantidadOperaciones++;
		}
	}

	public double saldoTotal() { //deposito más el invertido 
		return getSaldo() + getMontoInvertido();
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
	public ArrayList<Actividad> getActividades(){
		return actividades;
	}
	public int getCantOperaciones() {
		return cantidadOperaciones;
	}
	public double getMontoInvertido() {
		return montoInvertido;
	}

}
