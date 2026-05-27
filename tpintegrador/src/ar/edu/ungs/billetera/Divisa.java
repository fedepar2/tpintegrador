package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class Divisa extends Inversion {
	// guarda la cotización del activo al momento
	private double cotizacionInicial;

	public Divisa(double monto, Cuenta origen, int id, int plazo, String activo,
			      double tasa, boolean precancelable) {

		super(monto, origen, id, plazo, activo, tasa, precancelable);

		this.cotizacionInicial = Utilitarios.consultarCotizacion(activo);
	}

	@Override
	public void validarInversion() {
		// Verifica que el monto sea válido.
		if (getMonto() <= 0) {
			throw new IllegalArgumentException("Monto invalido.");
		}
		// Verifica que la cuenta tenga saldo suficiente.
		if (getMonto() > getOrigen().getSaldo()) {
			throw new IllegalArgumentException("Saldo insuficiente.");
		}
		
		this.getOrigen().validarInversion(this.getMonto());
		
		// verifica que la divisa exista
		Utilitarios.consultarCotizacion(getActivo());
	}

	@Override
	public double calcularRendimiento() {
		
		LocalDate fechaHoy = Utilitarios.hoy();
	    LocalDate fechaCreacion = getFecha();
	    
	    long dias = fechaHoy.toEpochDay() - fechaCreacion.toEpochDay();

	    double capitalEnDivisa = getMonto() / cotizacionInicial; 

	    double intereses = capitalEnDivisa * (getTasa() / 365.0) * dias;
	    
	    return intereses;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Inversion:\n");

		sb.append("■ fecha: ");
		sb.append(getFecha());
		sb.append("\n");

		sb.append("  origen: ");
		sb.append(getOrigen().getTitular().getDni());
		sb.append(" (");
		sb.append(getOrigen().getCvu());
		sb.append(")\n");

		sb.append("  desc: Vinculada a Divisa\n");
		
		sb.append("  monto: ");
		sb.append(getMonto());
		sb.append("\n");

		sb.append("  plazo: ");
		sb.append(getPlazo());
		sb.append("\n");

		if (getAprobada()) {
			sb.append("  [Aprobado]");
		}
		else {
			sb.append("  [Rechazado]");
		}

		return sb.toString();
	}

	public double getCotizacionInicial() {
		return cotizacionInicial;
	}
	
	@Override
	public void precancelar() {

	    double interesesAPagar = calcularRendimiento() / 2;
	    
	    double cotizacionHoy = Utilitarios.consultarCotizacion(getActivo());
	    double capitalEnDivisa = getMonto() / cotizacionInicial; 
	    
	    double totalPesosAAcreditar = (capitalEnDivisa + interesesAPagar) * cotizacionHoy;
	    
	    getOrigen().acreditar(totalPesosAAcreditar);
	    super.precancelar();
	}
}