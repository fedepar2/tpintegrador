package ar.edu.ungs.billetera;

public class CuentaPremium extends Cuenta {

	private static final double MINIMO = 500000; //constante de clase
	
	public CuentaPremium(String cvu, String alias, double depositoInicial, Usuario titular) {
		super(cvu, alias, depositoInicial, titular);		
	}

	@Override
	protected void validarOperacion(double monto) {
	    if (monto <= 0) {
	        throw new RuntimeException("El monto de la operación debe ser mayor a cero.");
	    }
	    
	    if (getSaldo() < monto) {
	        throw new RuntimeException("Saldo insuficiente para realizar la operación.");
	    }

	    if (saldoTotal() < MINIMO) {
	        throw new RuntimeException("Una cuenta premium no puede ser inferior a un patrimonio de $500.000.");
	    }
	}

}
