package ar.edu.ungs.billetera;

public class CuentaCorporativa extends Cuenta {

	private String cuit;
	private Empresa empresa; //contiene los DNIs autorizados
	
	public CuentaCorporativa(String alias, double depositoInicial, Usuario titular,
			                 String cuit, Empresa empresa) {
	    super(alias, depositoInicial, titular);
	    if (cuit == null || cuit.isEmpty()) {
	        throw new IllegalArgumentException("El CUIT es obligatorio para cuentas corporativas.");
	    }
	    if (empresa == null) {
	        throw new IllegalArgumentException("Una cuenta corporativa debe estar vinculada a"
	        		                            + "una empresa válida.");
	    }
	    this.cuit = cuit;
	    this.empresa = empresa;
	}
	
	@Override
	protected void validarInversion(double monto) {
	    validar(monto);
	}
	
	@Override
	protected void validarTransferencia(double monto) {
		validar(monto);
	}

	private void validar(double monto) {
		if (monto <= 0) {
	        throw new IllegalArgumentException("El monto de la operación debe ser mayor a cero.");
	    }

	    if (getSaldo() < monto) {
	        throw new IllegalArgumentException("Saldo insuficiente para realizar la operación.");
	    }

	    String dniTitular = getTitular().getDni();
	    if (!empresa.estaAutorizado(dniTitular)) {
	        throw new IllegalStateException("El titular con DNI " + dniTitular + 
	                                           " no tiene autorización para operar esta cuenta corporativa.");
	    }
	}
}