package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class Divisa extends Inversion {

	// Guarda la cotización del activo al momento
	// en que se creó la inversión.
	// Sirve para comparar luego con la cotización actual
	// y calcular la ganancia o pérdida.
	private double cotizacionInicial;

	public Divisa(double monto, Cuenta origen, int id, int plazo, String activo, double tasa, boolean precancelable) {

		// Las inversiones en divisas pueden ser precancelables o no
		super(monto, origen, id, plazo, activo, tasa, precancelable);

		// Guarda cotización inicial del activo
		this.cotizacionInicial = Utilitarios.consultarCotizacion(activo);
	}

	@Override
	public void validarInversion() {
		// Verifica que el monto sea válido.
		if (getMonto() <= 0) {
			throw new RuntimeException("Monto invalido.");
		}
		// Verifica que la cuenta tenga saldo suficiente.
		if (getMonto() > getOrigen().getSaldo()) {
			throw new RuntimeException("Saldo insuficiente.");
		}
		
		//this.getOrigen().validarOperacion(this.getMonto()); NO SÉ SI ES NECESARIO
		
		// Verifica que la divisa exista
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
	public void ejecutar() {

		try {
			validarInversion(); // Primero valida la inversión.
			getOrigen().invertir(this); // Descuenta saldo y registra inversión en la cuenta de origen
			aprobar();

		} catch (RuntimeException e) {
			rechazar();
			throw e; // relanza excepcion

		} finally {
			getOrigen().registrarActividad(this); // siempre se registra en el historial
		}
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