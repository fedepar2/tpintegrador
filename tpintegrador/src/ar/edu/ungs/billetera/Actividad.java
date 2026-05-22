package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Actividad {

	private LocalDate fecha;
	private double monto;
	private Cuenta origen; // por el toString(); necesario para inversión
	private boolean aprobada; // por el toString(); necesario para transferenca

	public Actividad(double monto, Cuenta origen) {
		if (monto <= 0) {
			throw new RuntimeException("Monto invalido.");
		}

		if (origen == null) {
			throw new RuntimeException("Cuenta origen invalida.");
		}

		this.fecha = Utilitarios.hoy(); // uso de la clase Utilitarios
		this.monto = monto;
		this.origen = origen;
		this.aprobada = false; // se marca como aprobada tras la ejecución exitosa
	}

	public abstract void ejecutar();

	public abstract String toString(); // hace falta?

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
