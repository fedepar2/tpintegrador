package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class RentaFija extends Inversion {

	public RentaFija(double monto, Cuenta origen, int id, int plazo, String activo, double tasa,
			boolean precancelable) {
		super(monto, origen, id, plazo, activo, tasa, precancelable);
	}

	@Override
	public double calcularRendimiento() {
		return getMonto() * getTasa();
	}

	@Override
	public void validarInversion() {
		if (this.getMonto() <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}

		this.getOrigen().validarInversion(this.getMonto());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Inversion:\n");
		
		sb.append("■ fecha: ");
		sb.append(getFecha());
		sb.append("\n");

		sb.append("  origen: ");
		sb.append(getOrigen().getTitular().getDni());
		sb.append(" (");
		sb.append(getOrigen().getCvu());
		sb.append(")\n");

		sb.append("  desc: Renta fija\n");
		
		sb.append("  monto: ");
		sb.append(getMonto());
		sb.append("\n");

		sb.append("  plazo: ");
		sb.append(getPlazo());
		sb.append("\n");

		if (getAprobada()) {
			sb.append("  [Aprobado]");
		}
		else {
			sb.append("  [Rechazado]");
		}

		return sb.toString();
	}

	@Override
	public void precancelar() {
		LocalDate fechaHoy = Utilitarios.hoy();
		LocalDate fechaCreacion = this.getFecha();

		long dias = fechaHoy.toEpochDay() - fechaCreacion.toEpochDay();

		double intereses = calcularRendimiento() / 365.0 * dias;

		double interesesAPagar = intereses / 2.0;

		double totalPesosAAcreditar = this.getMonto() + interesesAPagar;

		this.getOrigen().acreditar(totalPesosAAcreditar);
		super.precancelar();
	}
}
