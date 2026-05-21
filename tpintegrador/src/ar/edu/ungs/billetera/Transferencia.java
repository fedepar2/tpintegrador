package ar.edu.ungs.billetera;

public class Transferencia extends Actividad {
	private Cuenta destino;
	
	public Transferencia(double monto, Cuenta origen, Cuenta destino) {
		super(monto, origen);
		this.destino = destino;
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

}
