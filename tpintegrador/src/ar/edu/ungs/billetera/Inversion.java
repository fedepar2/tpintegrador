package ar.edu.ungs.billetera;

public abstract class Inversion extends Actividad {

	private int idInversion; //para punto 13
	private int plazo;
	private String activo; //para consultar y actualizar cotización...
	private double tasa; //FL ahora tiene tasa
	private boolean precancelada; //estado
	private boolean precancelable; //regla de negocio
	
	public Inversion(double monto, Cuenta origen, int id, int plazo, String activo,
			         double tasa, boolean precancelable) {
        super(monto, origen);
        if(id <= 0 || plazo <= 0 || tasa < 0) {
        	throw new RuntimeException("ID, plazo y tasa son obligatorios.");
        }
        if(activo == null) {
        	throw new RuntimeException("Un activo y tipo de inversión son obligatorios.");
        }
        this.idInversion = id;
        this.plazo = plazo;
        this.activo = activo;
        this.tasa = tasa;
        this.precancelada = false;
        this.precancelable = precancelable;
    }

	
	@Override
	public void ejecutar() {
	    try {
	    	//validarInversion();
	    	getOrigen().invertir(this); 

	    	getOrigen().getTitular().actualizarTotalInvertido(this.getMonto());
	    	this.setAprobada(true);
	    }
	    catch (RuntimeException e) {
	    	this.setAprobada(false);
	    	throw e;
	    }
	    finally {
	        this.getOrigen().registrarActividad(this);
	    }
	}

	public abstract String toString();
	
	public abstract double calcularRendimiento();
	
	//public abstract void validarInversion(); es necesario o debería ser abstracto de Actividad?

}
