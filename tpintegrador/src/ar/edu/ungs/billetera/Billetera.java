package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Billetera implements IBilletera {

	private Map<String, Usuario> usuarios; // dni : String
	private Map<String, Cuenta> cuentas; // alias : String
	private Map<String, Empresa> empresas; // cuit : String
	private List<Actividad> actividades;
	private Map<String, String> aliasToCvu; // alias -> cvu (agregado)

	public Billetera() {
		this.usuarios = new HashMap<>();
		this.cuentas = new HashMap<>();
		this.empresas = new HashMap<>();
		this.actividades = new ArrayList<>();
		this.aliasToCvu = new HashMap<>(); // agregado
	}

	// no modificar estos métodos
	@Override
	public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, // punto 11
			String nombreContacto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) { // punto 12
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarUsuario(String dni, String nombre, String telefono, String email) { // punto 1
		// el interfaz dice verificar si son invalidos pero el constructor ya hace
		// eso...
		if (usuarios.containsKey(dni)) {
			throw new RuntimeException("El usuario con DNI " + dni + " ya se encuentra registrado.");
		}

		Usuario nuevoUsuario = new Usuario(dni, nombre, telefono, email);
		usuarios.put(dni, nuevoUsuario);
	}

	@Override
	public String crearCuentaRegular(String dniUsuario, String alias) {

		if (!usuarios.containsKey(dniUsuario)) {
			throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe.");
		}

		// ahora validamos alias en el nuevo map
		if (aliasToCvu.containsKey(alias)) {
			throw new RuntimeException("La cuenta con alias " + alias + " ya existe.");
		}

		Usuario titular = usuarios.get(dniUsuario);

		Cuenta regular = new CuentaRegular(alias, 0, titular);

		// guardamos por CVU
		cuentas.put(regular.getCvu(), regular);

		// guardamos alias -> cvu
		aliasToCvu.put(alias, regular.getCvu());

		titular.agregarCuenta(regular);

		return regular.getCvu();
	}

	@Override
	public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {

		// Verifica que el usuario exista
		if (!usuarios.containsKey(dniUsuario)) {
			throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe.");
		}

		// Verifica que el alias no esté repetido
		if (aliasToCvu.containsKey(alias)) {
			throw new RuntimeException("El alias " + alias + " ya se encuentra registrado.");
		}

		// Validación del mínimo requerido
		if (depositoInicial < 500000) {
			throw new RuntimeException("El deposito inicial debe ser mayor a 500.000 pesos.");
		}

		// Obtiene el usuario titular
		Usuario titular = usuarios.get(dniUsuario);

		// Crea la cuenta premium
		Cuenta premium = new CuentaPremium(alias, depositoInicial, titular);

		// Guarda la cuenta usando CVU como clave
		cuentas.put(premium.getCvu(), premium);

		// Guarda relación alias -> cvu
		aliasToCvu.put(alias, premium.getCvu());

		// Agrega la cuenta al usuario
		titular.agregarCuenta(premium);

		// Devuelve el CVU generado
		return premium.getCvu();
	}

	@Override
	public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {

		// Verifica que el usuario exista
		if (!usuarios.containsKey(dniUsuario)) {
			throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe.");
		}

		// Verifica que el alias no esté repetido
		if (aliasToCvu.containsKey(alias)) {
			throw new RuntimeException("El alias " + alias + " ya se encuentra registrado.");
		}

		// Verifica que la empresa exista
		if (!empresas.containsKey(cuitEmpresa)) {
			throw new RuntimeException("La empresa con CUIT " + cuitEmpresa + " no existe.");
		}

		Empresa empresa = empresas.get(cuitEmpresa);

		// Verifica autorización del usuario
		if (!empresa.estaAutorizado(dniUsuario)) {
			throw new RuntimeException("El usuario no está autorizado por la empresa.");
		}

		// Obtiene usuario titular
		Usuario titular = usuarios.get(dniUsuario);

		// Crea cuenta corporativa
		Cuenta corporativa = new CuentaCorporativa(alias, 0, titular, cuitEmpresa, empresa);

		// Guarda usando CVU como clave
		cuentas.put(corporativa.getCvu(), corporativa);

		// Guarda relación alias -> cvu
		aliasToCvu.put(alias, corporativa.getCvu());

		// Agrega cuenta al usuario
		titular.agregarCuenta(corporativa);

		// Registra cuenta dentro de empresa
		empresa.agregarCuentaCorporativa((CuentaCorporativa) corporativa);

		// Devuelve CVU generado
		return corporativa.getCvu();
	}

	@Override
	public List<String> obtenerCuentas(String dniUsuario) { // punto 3
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) { // punto 4
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {

		Cuenta origen = cuentas.get(cvuOrigen);
		Cuenta destino = cuentas.get(cvuDestino);

		if (origen == null || destino == null) {
			throw new IllegalArgumentException("Cuenta inexistente.");
		}

		Transferencia transferencia = new Transferencia(monto, origen, destino);

		transferencia.ejecutar();
	}

	@Override
	public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) { // punto 6
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, // punto 6
			double tasa) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) { // punto 6
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) { // punto 13
		// TODO Auto-generated method stub

	}

	@Override
	public String consultarCvu(String alias) {

		if (!aliasToCvu.containsKey(alias)) {
			throw new IllegalArgumentException("El alias no está registrado.");
		}

		return aliasToCvu.get(alias);
	}

	@Override
	public List<String> consultarHistorialGlobal() { // punto 7
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialCuenta(String cvu) { // punto 8
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> consultarHistorialUsuario(String dniUsuario) {

		if (!usuarios.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("El usuario no existe.");
		}

		Usuario usuario = usuarios.get(dniUsuario);

		List<String> historial = new ArrayList<>();

		// recorrer cuentas del usuario
		for (Cuenta cuenta : usuario.getCuentas().values()) {

			// recorrer actividades de cada cuenta
			for (Actividad actividad : cuenta.getActividades()) {

				historial.add(actividad.toString());
			}
		}

		return historial;
	}

	@Override
	public double obtenerTotalInvertido(String dniUsuario) { // punto 9
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> cuentasConMayorVolumen(int cantidadTop) { // punto 10
		// TODO Auto-generated method stub
		return null;
	}

	// Se debe poder imprimir “Billetera” mostrando sus datos en formato adecuado
	// para poder comprenderlo.
	@Override
	public String toString() {

		return null;
	}
}
