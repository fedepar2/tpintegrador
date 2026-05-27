package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {

	private int idInversion;
	private int plazo;
	private String activo;
	private double tasa; // FL tiene tasa
	private boolean precancelada; // estado
	private boolean precancelable; // regla de negocio

	public Inversion(double monto, Cuenta origen, int id, int plazo, String activo, double tasa,
			boolean precancelable) {
		super(monto, origen);
		if (id <= 0 || plazo <= 0 || tasa < 0) {
			throw new IllegalArgumentException("ID, plazo y tasa son obligatorios.");
		}
		if (activo == null) {
			throw new IllegalArgumentException("Un activo y tipo de inversión son obligatorios.");
		}
		this.idInversion = id;
		this.plazo = plazo;
		this.activo = activo;
		this.tasa = tasa;
		this.precancelada = false;
		this.precancelable = precancelable;
	}

	@Override
	public void ejecutar() { // sirve para cualquier tipo de inversion
		try {
			validarInversion();
			getOrigen().invertir(this);
			aprobar();

		} catch (IllegalArgumentException e) {
			rechazar();
			throw e;
		}
		finally {
			getOrigen().registrarActividad(this);
		}
	}

	public abstract String toString();

	public abstract double calcularRendimiento();

	public abstract void validarInversion();

	public int getPlazo() {
		return plazo;
	}

	public String getActivo() {
		return activo;
	}

	public double getTasa() {
		return tasa;
	}

	public int getIdInversion() {
		return idInversion;
	}
	
	public boolean getPrecancelable() {
		return precancelable;
	}

	public boolean estaPrecancelada() {
		return precancelada;
	}
	
	public void precancelar() {
	    precancelada = true;
	}
}
