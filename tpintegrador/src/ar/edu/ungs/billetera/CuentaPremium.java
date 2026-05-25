package ar.edu.ungs.billetera;

public class CuentaPremium extends Cuenta {

	private static final double MINIMO = 500000; // constante de clase

	public CuentaPremium(String alias, double depositoInicial, Usuario titular) {
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

		// validar saldo final
		if (saldoTotal() - monto < MINIMO) {
			throw new RuntimeException("Una cuenta premium no puede quedar debajo de $500.000.");
		}
	}

}
