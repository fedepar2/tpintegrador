package ar.edu.ungs.billetera;

public class RentaFija extends Inversion {

	public RentaFija(double monto, Cuenta origen, int id, int plazo, String activo, double tasa,boolean precancelable,
			String tipo) {
		super(monto, origen, id, plazo, activo, tasa, precancelable, tipo); //precancelable siempre true?
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
