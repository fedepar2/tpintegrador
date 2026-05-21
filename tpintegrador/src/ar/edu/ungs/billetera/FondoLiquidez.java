package ar.edu.ungs.billetera;

public class FondoLiquidez extends Inversion {

	private static final double MINIMO = 20000000;
	
	public FondoLiquidez(double monto, Cuenta origen, int id, int plazo, String activo, //activo y tipo siempre los mismos
			String tipo) {
		super(monto, origen, id, plazo, activo, 0.08, false, tipo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calcularRendimiento() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void validarInversion() {
		// TODO Auto-generated method stub

	}

}
