package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {

	private int idInversion; // para punto 13
	private int plazo;
	private String activo; // para consultar y actualizar cotización...
	private double tasa; // FL ahora tiene tasa
	private boolean precancelada; // estado
	private boolean precancelable; // regla de negocio
	private LocalDate fechaVencimiento; // porque las inversiones tienen fecha de vencimiento

	public Inversion(double monto, Cuenta origen, int id, int plazo, String activo, double tasa,
			boolean precancelable) {
		super(monto, origen);
		if (id <= 0 || plazo <= 0 || tasa < 0) {
			throw new RuntimeException("ID, plazo y tasa son obligatorios.");
		}
		if (activo == null) {
			throw new RuntimeException("Un activo y tipo de inversión son obligatorios.");
		}
		this.idInversion = id;
		this.plazo = plazo;
		this.activo = activo;
		this.tasa = tasa;
		this.precancelada = false;
		this.precancelable = precancelable;
		this.fechaVencimiento = getFecha().plusDays(plazo); // se usa para calcular la fecha de vencimiento de la
															// inversion
	}

	@Override
	public void ejecutar() { // no es abstracto popr que sirve para cualquier tipo de inversion
		try {
			getOrigen().invertir(this);
			aprobar();

		} catch (RuntimeException e) {
			rechazar();
			throw e;
		} finally {
			this.getOrigen().registrarActividad(this);
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

}
