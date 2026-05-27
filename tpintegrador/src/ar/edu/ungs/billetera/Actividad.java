package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Actividad {

	private LocalDate fecha;
	private double monto;
	private Cuenta origen;
	private boolean aprobada;

	public Actividad(double monto, Cuenta origen) {
		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}

		if (origen == null) {
			throw new IllegalArgumentException("Cuenta origen invalida.");
		}

		this.fecha = Utilitarios.hoy();
		this.monto = monto;
		this.origen = origen;
		this.aprobada = false; // se marca como aprobada tras la ejecución exitosa
	}

	public abstract void ejecutar();

	public abstract String toString();

	public LocalDate getFecha() {
		return fecha;
	}

	protected void aprobar() {
		this.aprobada = true;
	}

	protected void rechazar() {
		this.aprobada = false;
	}

	public boolean getAprobada() {
		return aprobada;
	}

	public double getMonto() {
		return monto;
	}

	public Cuenta getOrigen() {
		return origen;
	}

}
