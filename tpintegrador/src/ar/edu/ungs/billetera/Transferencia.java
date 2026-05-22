package ar.edu.ungs.billetera;

public class Transferencia extends Actividad {
	private Cuenta destino;
	
	public Transferencia(double monto, Cuenta origen, Cuenta destino) {
		super(monto, origen);
		if(destino == null) {
			throw new RuntimeException("Una cuenta de destino es obligatoria.");
		}
		this.destino = destino;
	}
	
	@Override
	public void ejecutar() {
		try {
			getOrigen().transferir(getDestino(), getMonto());
		    setAprobada(true); 
		}
		catch (RuntimeException e) {
		    setAprobada(false);
		    throw e; 
		}
		finally {
		    getOrigen().registrarActividad(this);
		    getDestino().registrarActividad(this);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Cuenta getDestino() {
		return destino;
	}

}
