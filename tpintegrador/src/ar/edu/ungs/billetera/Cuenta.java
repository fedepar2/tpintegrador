package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {

	private String cvu;
	private String alias;
	private Usuario titular; // para el toString()
	private double saldo;
	private List<Actividad> actividades;
	private int cantidadOperaciones;
	private double montoInvertido; // para que punto 9 sea O(1)

	public Cuenta(String cvu, String alias, double depositoInicial, Usuario titular) {
		if (cvu == null || alias == null || titular == null) { //habría que también validar si tienen el formato correcto?
			throw new RuntimeException("CVU, Alias y Titular deben tener un valor.");
		}
		if (depositoInicial < 0) {
			throw new RuntimeException("El depósito inicial no puede ser negativo.");
		}
		this.cvu = cvu;
		this.alias = alias;
		this.saldo = depositoInicial;
		this.titular = titular;
		this.actividades = new ArrayList<>();
		this.cantidadOperaciones = 0;
	}

	protected abstract void validarOperacion(double monto); // así hay override en subclases

	public void transferir(Cuenta destino, double monto) {
		Transferencia transf = new Transferencia(monto, this, destino);
		try {
			validarOperacion(monto);

			saldo -= monto; // quedaría mejor si se hiciera un nuevo método acreditar
			destino.saldo += monto;
			transf.setAprobada(true);
		} catch (RuntimeException e) {
			transf.setAprobada(false); // ya era false por defecto al crear Transferencia
			// deberíamos relanzar la excepcion en terminal?
		} finally {
			if (transf != null) {
				registrarActividad(transf);
				if (transf.getAprobada()) {
					destino.registrarActividad(transf);
				}
			}
		}
	}

	public void invertir(Inversion inv) {
		try {
			validarOperacion(inv.getMonto());

			saldo -= inv.getMonto();
			montoInvertido += inv.getMonto();
			inv.setAprobada(true);
			titular.actualizarTotalInvertido(inv.getMonto());

		} catch (RuntimeException e) {
			inv.setAprobada(false); // ya era false por defecto al crear Inversion
			// deberíamos relanzar la excepcion en terminal?
		} finally {
			if (inv != null) { //no sé si es necesario
				registrarActividad(inv);
			}
		}
	}

	public void registrarActividad(Actividad act) {
		if (act != null) {
			actividades.add(act);
			if (act.getAprobada()) {
				cantidadOperaciones++;
			}
		}
		//añadir excepción?
	}

	public double saldoTotal() { // deposito más el invertido
		return getSaldo() + getMontoInvertido();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getClass().getSimpleName().replace("Cuenta", "")); // tipo de cuenta
		sb.append(": ");
		sb.append(getAlias());
		sb.append(" (");
		sb.append(getCvu());
		sb.append(")");
		
		return sb.toString();
	}

	public String getCvu() {
		return cvu;
	}
	public String getAlias() {
		return alias;
	}
	public double getSaldo() {
		return saldo;
	}
	public Usuario getTitular() {
		return titular;
	}
	public List<Actividad> getActividades() {
		return actividades;
	}
	public int getCantOperaciones() {
		return cantidadOperaciones;
	}
	public double getMontoInvertido() {
		return montoInvertido;
	}

}
