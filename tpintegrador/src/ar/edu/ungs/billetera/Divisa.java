package ar.edu.ungs.billetera;

public class Divisa extends Inversion {

	// Guarda la cotización del activo al momento
	// en que se creó la inversión.
	// Sirve para comparar luego con la cotización actual
	// y calcular la ganancia o pérdida.
	private double cotizacionInicial;

	public Divisa(double monto, Cuenta origen, int id, int plazo, String activo, double tasa) {

		// Las inversiones en divisas son precancelables
		super(monto, origen, id, plazo, activo, tasa, true);

		// Guarda cotización inicial del activo
		this.cotizacionInicial = Utilitarios.consultarCotizacion(activo);
	}

	@Override
	public void validarInversion() {
		// Verifica que el monto sea válido.
		if (getMonto() <= 0) {
			throw new RuntimeException("Monto invalido.");
		}
		// Verifica que la cuenta tenga saldo suficiente.
		if (getMonto() > getOrigen().getSaldo()) {
			throw new RuntimeException("Saldo insuficiente.");
		}

		// Verifica que la divisa exista
		Utilitarios.consultarCotizacion(getActivo());
	}

	@Override
	public double calcularRendimiento() {
		// Obtiene la cotización actual del activo.
		double cotizacionActual = Utilitarios.consultarCotizacion(getActivo());
		// Calcula la variación porcentual del activo.
		double variacion = (cotizacionActual - cotizacionInicial) / cotizacionInicial;

		// rendimiento por variación + tasa
		return getMonto() * (variacion + getTasa());
	}

	@Override
	public void ejecutar() {

		try {
			validarInversion(); // Primero valida la inversión.
			getOrigen().invertir(this); // Descuenta saldo y registra inversión en la cuenta de origen
			aprobar();

		} catch (RuntimeException e) {
			rechazar();
			throw e; // relanza excepcion

		} finally {
			getOrigen().registrarActividad(this); // siempre se registra en el historial
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("origen: ");
		sb.append(getOrigen().getTitular().getDni());
		sb.append(" (");
		sb.append(getOrigen().getCvu());
		sb.append(")\n");

		sb.append("desc: Inversion Divisa ");
		sb.append(getActivo());
		sb.append("\n");

		sb.append("monto: ");
		sb.append(getMonto());
		sb.append("\n");

		sb.append("plazo: ");
		sb.append(getPlazo());
		sb.append("\n");

		sb.append(getAprobada() ? "Aprobado" : "Rechazado");

		return sb.toString();
	}

	public double getCotizacionInicial() {
		return cotizacionInicial;
	}
}