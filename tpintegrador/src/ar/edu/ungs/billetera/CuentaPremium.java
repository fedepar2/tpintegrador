package ar.edu.ungs.billetera;

public class CuentaPremium extends Cuenta {

	private static final double MINIMO = 500000; //constante de clase
	
	public CuentaPremium(String cvu, String alias, double depositoInicial, Usuario titular) {
		super(cvu, alias, depositoInicial, titular);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void validarOperacion(double monto) {
		// TODO Auto-generated method stub

	}

}
