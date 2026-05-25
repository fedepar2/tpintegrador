package ar.edu.ungs.billetera;

public class CuentaRegular extends Cuenta {

	private static final double LIMITE = 5000000; // constante de clase

	public CuentaRegular(String alias, double depositoInicial, Usuario titular) {
		super(alias, depositoInicial, titular);
	}

	@Override
	protected void validarOperacion(double monto) {

		if (monto <= 0) {
			throw new IllegalStateException("Monto invalido.");
		}

		if (saldoTotal() + monto > LIMITE) {

			System.out.println("LANZO EXCEPCION");

			throw new IllegalStateException("Una cuenta regular no puede superar los 5 millones.");
		}
	}
}
