package ar.edu.ungs.billetera;

public class CuentaRegular extends Cuenta {

	private static final double LIMITE = 5000000; //constante de clase
	
	public CuentaRegular(String cvu, String alias, double depositoInicial, Usuario titular) {
		super(cvu, alias, depositoInicial, titular);
		// TODO Auto-generated constructor stub

	}

	@Override
	protected void validarOperacion(double monto) {
		// TODO Auto-generated method stub

	}
}
