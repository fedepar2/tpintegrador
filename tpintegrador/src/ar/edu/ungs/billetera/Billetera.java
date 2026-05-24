package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Billetera implements IBilletera {
	
	private Map<String, Usuario> usuarios; //dni : String
	private Map<String, Cuenta> cuentas; //alias : String
	private Map<String, Empresa> empresas; //cuit : String
	private List<Actividad> actividades;

	public Billetera() {
		this.usuarios = new HashMap<>();
		this.cuentas = new HashMap<>();
		this.empresas = new HashMap<>();
		this.actividades = new ArrayList<>();
	}
	//no modificar estos métodos
	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, //punto 11
			String nombreContacto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) { //punto 12
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) { //punto 1
		//el interfaz dice verificar si son invalidos pero el constructor ya hace eso...
		if(usuarios.containsKey(dni)) {
			throw new RuntimeException("El usuario con DNI " + dni + " ya se encuentra registrado.");
		}
		
		Usuario nuevoUsuario = new Usuario(dni, nombre, telefono, email);
		usuarios.put(dni, nuevoUsuario);
	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) { //punto 2
		if(!usuarios.containsKey(dniUsuario)) {
			throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe.");
		}
		if(cuentas.containsKey(alias)) {
			throw new RuntimeException("La cuenta con alias " + alias + " ya se encuentra registrado.");
		}
		
		Usuario titular = usuarios.get(dniUsuario);
		Cuenta regular = new CuentaRegular(alias, 0, titular);
		cuentas.put(alias, regular);
		titular.agregarCuenta(regular);
		
		return regular.getCvu();
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) { //punto 2
		if(!usuarios.containsKey(dniUsuario)) {
			throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe.");
		}
		if(cuentas.containsKey(alias)) {
			throw new RuntimeException("La cuenta con alias " + alias + " ya se encuentra registrado.");
		}
		if(depositoInicial < 500000 ) {
			throw new RuntimeException("El deposito inicial debe ser mayor de 500.000 pesos");
		}
		
		Usuario titular = usuarios.get(dniUsuario);
		Cuenta premium = new CuentaPremium(alias, depositoInicial, titular);
		cuentas.put(alias, premium);
		titular.agregarCuenta(premium);
		
		return premium.getCvu();
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) { //punto 2
		if(!usuarios.containsKey(dniUsuario)) {
			throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe.");
		}
		if(cuentas.containsKey(alias)) {
			throw new RuntimeException("La cuenta con alias " + alias + " ya se encuentra registrado.");
		}
		if(!empresas.containsKey(cuitEmpresa)) {
			throw new RuntimeException("La empresa con CUIT " + cuitEmpresa + " no existe.");
		}
		Empresa empresa = empresas.get(cuitEmpresa);
		if (!empresa.estaAutorizado(dniUsuario)) {
			throw new RuntimeException("El usuario no está autorizado por la empresa.");
		}
		
		Usuario titular = usuarios.get(dniUsuario);
		Cuenta corporativa =  new CuentaCorporativa(alias, 0, titular, cuitEmpresa, empresa);
		cuentas.put(alias, corporativa);
		titular.agregarCuenta(corporativa);
		
		return corporativa.getCvu();
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) { //punto 3
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) { //punto 4
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) { //punto 5
		// TODO Auto-generated method stub

	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) { //punto 6
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, //punto 6
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) { //punto 6
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) { //punto 13
		// TODO Auto-generated method stub

	}

	@Override
	public String consultarCvu(String alias) { //punto 14
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialGlobal() { //punto 7
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) { //punto 8
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) { //punto 8
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) { //punto 9
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) { //punto 10
		// TODO Auto-generated method stub
		return null;
	}
	
	//Se debe poder imprimir “Billetera” mostrando sus datos en formato adecuado para poder comprenderlo.
	@Override
	public String toString() {
		
		return null;
	}
}
