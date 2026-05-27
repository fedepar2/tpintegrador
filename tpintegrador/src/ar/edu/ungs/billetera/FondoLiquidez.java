package ar.edu.ungs.billetera;

public class FondoLiquidez extends Inversion {

	// Monto mínimo requerido para invertir
	private static final double MINIMO = 20000000;

	// Constantes propias del tipo de inversión
	private static final String ACTIVO = "FCI";
	private static final double TASA = 0.08;

	public FondoLiquidez(double monto, Cuenta origen, int id, int plazo) {

		super(monto, origen, id, plazo, ACTIVO, TASA, false);
	}

	@Override
	public void validarInversion() {

		// El monto debe ser positivo
		if (getMonto() <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}

		// Verifica saldo suficiente
		if (getMonto() > getOrigen().getSaldo()) {
			throw new IllegalArgumentException("Saldo insuficiente.");
		}

		// Verifica monto mínimo requerido
		if (getMonto() < MINIMO) {
			throw new IllegalArgumentException("El monto minimo para Fondo Liquidez es " + MINIMO);
		}
		
		this.getOrigen().validarInversion(this.getMonto());
	}

	@Override
	public double calcularRendimiento() {
		return getMonto() * getTasa();
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

		sb.append("  desc: Fondo de Liquidez Empresarial\n");
		
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
}
