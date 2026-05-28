package ar.edu.ungs.billetera;

public class CuentaRegular extends Cuenta {

	private static final double LIMITE = 5000000; // constante de clase

	public CuentaRegular(String alias, double depositoInicial, Usuario titular) {
		super(alias, depositoInicial, titular);
	}

	@Override
	protected void validarTransferencia(double monto) {
	
		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}

		if (saldoTotal() + monto > LIMITE) {
			throw new IllegalStateException("Una cuenta regular no puede superar los 5 millones.");
		}
		
	}
	
	@Override
	protected void validarInversion(double monto) {
		if (monto <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}
		
		if (getSaldo() < monto) {
	        throw new IllegalArgumentException("Saldo disponible insuficiente para iniciar la inversion.");
	    }
		
	}
}
