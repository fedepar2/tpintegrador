package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class RentaFija extends Inversion {

	public RentaFija(double monto, Cuenta origen, int id, int plazo, String activo, double tasa,
			boolean precancelable) {
		super(monto, origen, id, plazo, activo, tasa, precancelable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calcularRendimiento() {
		return this.getMonto() * this.getTasa();
	}

	@Override
	public void validarInversion() {
		if (this.getMonto() <= 0) {
			throw new RuntimeException("Monto invalido.");
		}
		// Delegamos la validación de fondos a la cuenta origen (bajo acoplamiento)
		this.getOrigen().validarInversion(this.getMonto());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("inversion: fecha: ");
		sb.append(this.getFecha());
		sb.append("\n");

		sb.append("origen: ");
		sb.append(this.getOrigen().getTitular().getDni());
		sb.append(" (");
		sb.append(this.getOrigen().getCvu());
		sb.append(")\n");

		sb.append("desc: Renta Fija\n");
		sb.append("monto: ");
		sb.append(this.getMonto());
		sb.append("\n");

		sb.append("plazo: ");
		sb.append(this.getPlazo());
		sb.append("\n");

		if (getAprobada()) {
			sb.append("[Aprobado]");
		} else {
			sb.append("[Rechazado]");
		}

		return sb.toString();
	}

	@Override
	public void precancelar() { // para testPrecancelarInversionRentaFija
		LocalDate fechaHoy = Utilitarios.hoy();
		LocalDate fechaCreacion = this.getFecha();

		long dias = fechaHoy.toEpochDay() - fechaCreacion.toEpochDay();

		double intereses = getMonto() * getTasa() / 365.0 * dias;

		double interesesAPagar = intereses / 2.0;

		double totalPesosAAcreditar = this.getMonto() + interesesAPagar;

		this.getOrigen().acreditar(totalPesosAAcreditar);
		super.precancelar();
	}
}
