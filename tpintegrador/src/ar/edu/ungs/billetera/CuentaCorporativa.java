package ar.edu.ungs.billetera;

public class CuentaCorporativa extends Cuenta {

	private String cuit;
	private Empresa empresa; //contiene los DNIs autorizados
	
	public CuentaCorporativa(String cvu, String alias, double depositoInicial, Usuario titular, String cuit, Empresa empresa) {
	    super(cvu, alias, depositoInicial, titular);
	    if (cuit == null) { //menor a 11...
	        throw new RuntimeException("El CUIT es obligatorio para cuentas corporativas.");
	    }
	    if (empresa == null) {
	        throw new RuntimeException("Una cuenta corporativa debe estar vinculada a una empresa válida.");
	    }
	    this.cuit = cuit;
	    this.empresa = empresa;
	}
	
	@Override
	protected void validarOperacion(double monto) {
	    if (monto <= 0) {
	        throw new RuntimeException("El monto de la operación debe ser mayor a cero.");
	    }

	    if (getSaldo() < monto) {
	        throw new RuntimeException("Saldo insuficiente para realizar la operación.");
	    }

	    String dniTitular = getTitular().getDni();
	    if (!empresa.estaAutorizado(dniTitular)) {
	        throw new RuntimeException("El titular con DNI " + dniTitular + 
	                                   " no tiene autorización para operar esta cuenta corporativa.");
	    }
	}

}