package ar.edu.ungs.billetera;

public class CuentaCorporativa extends Cuenta {

	private String cuit;
	private Empresa empresa; //contiene los DNIs autorizados
	
	public CuentaCorporativa(String cvu, String alias, double depositoInicial, Usuario titular, String cuit, Empresa empresa) {
		super(cvu, alias, depositoInicial, titular);
		this.cuit = cuit;
		this.empresa = empresa;
	}
	
	@Override
	protected void validarOperacion(double monto) {
		// TODO Auto-generated method stub
	}

}