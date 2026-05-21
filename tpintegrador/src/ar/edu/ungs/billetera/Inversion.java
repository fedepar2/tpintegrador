package ar.edu.ungs.billetera;

public abstract class Inversion extends Actividad {

	private int idInversion; //para punto 13
	private int plazo;
	private String activo; //para consultar y actualizar cotización...
	private double tasa; //FL ahora tiene tasa
	private boolean precancelada; //estado
	private boolean precancelable; //regla de negocio
	private String tipoInversion; //para toString()...
	
	public Inversion(double monto, Cuenta origen, int id, int plazo, String activo, double tasa, boolean precancelable, String tipo) {
        super(monto, origen);
        this.idInversion = id;
        this.plazo = plazo;
        this.activo = activo;
        this.tasa = tasa;
        this.precancelada = false;
        this.precancelable = precancelable;
        this.tipoInversion = tipo;
    }

	
	@Override
	public void ejecutar() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public abstract double calcularRendimiento();
	
	public abstract void validarInversion();

}
