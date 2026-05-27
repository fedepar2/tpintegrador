package ar.edu.ungs.billetera;

public class Transferencia extends Actividad {
	private Cuenta destino;
	
	public Transferencia(double monto, Cuenta origen, Cuenta destino) {
		super(monto, origen);
		if(destino == null) {
			throw new RuntimeException("Una cuenta de destino es obligatoria.");
		}
		if(origen.equals(destino)) {
			throw new RuntimeException("La cuenta de origen no puede ser igual a la de destino");
		}
		this.destino = destino;
	}
	
	@Override
	public void ejecutar() {
		try {
			//validarInversion();
			getOrigen().transferir(getDestino(), getMonto());
			aprobar(); 
		}
		catch (RuntimeException e) {
			rechazar();
		    throw e; 
		}
		finally {
		    getOrigen().registrarActividad(this);
		    getDestino().registrarActividad(this);
		}
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Transferencia:\n");
	    
	    sb.append("■ fecha: ");
	    sb.append(getFecha());
	    sb.append("\n");
	    
	    sb.append("  origen: ");
	    sb.append(getOrigen().getTitular().getDni());
	    sb.append(" (");
	    sb.append(getOrigen().getCvu());
	    sb.append(")\n");
	    
	    sb.append("  destino: ");
	    sb.append(getDestino().getTitular().getDni());
	    sb.append(" (");
	    sb.append(getDestino().getCvu());
	    sb.append(")\n");
	    
	    sb.append("  monto: ");
	    sb.append(getMonto());
	    sb.append("\n");
	    
	    if (getAprobada()) {
	        sb.append("  [Aprobado]");
	    }
	    else {
	        sb.append("  [Rechazado]");
	    }
	    
	    return sb.toString();
	}
	
	public Cuenta getDestino() {
		return destino;
	}

}
