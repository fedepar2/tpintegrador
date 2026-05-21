package ar.edu.ungs.billetera;

public class Divisa extends Inversion {

	private String divisa;
	
	public Divisa(double monto, Cuenta origen, int id, int plazo, String activo, double tasa, boolean precancelable,
			String tipo, String divisa) {
		super(monto, origen, id, plazo, activo, tasa, precancelable, tipo); //precancelable siempre true?
		this.divisa = divisa;
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
