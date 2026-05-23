package ar.edu.ungs.billetera;

public class CuentaRegular extends Cuenta {

	private static final double LIMITE = 5000000; //constante de clase
	
	public CuentaRegular(String cvu, String alias, double depositoInicial, Usuario titular) {
		super(alias, depositoInicial, titular);
	}

	@Override
	protected void validarOperacion(double monto) {
	    if (monto <= 0) {
	        throw new RuntimeException("El monto de la operación debe ser mayor a cero.");
	    }

	    if (getSaldo() < monto) {
	        throw new RuntimeException("Saldo insuficiente para realizar la operación.");
	    }

	    if (saldoTotal() > LIMITE) { //el dinero invertido se considera como dinero en cuenta
	        throw new RuntimeException("Una cuenta regular no puede superar el patrimonio de $5.000.000.");
	    }
	}
}
