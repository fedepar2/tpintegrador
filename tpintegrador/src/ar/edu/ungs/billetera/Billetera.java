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
	    if (!usuarios.containsKey(dniUsuario)) {
	        throw new RuntimeException("El usuario con DNI " + dniUsuario + " no existe."); // [4, 5]
	    }
	    
	    Usuario titular = usuarios.get(dniUsuario);
	    
	    List<String> resultado = new ArrayList<>();

	    for (Cuenta cuenta : titular.listarCuentas()) { // for-each
	        resultado.add(cuenta.toString());
	    }

	    return resultado;
	}

	@Override
	public double obtenerSaldoDisponible(String cvu) { // punto 4
	    if (cvu == null || cvu.trim().isEmpty()) {
	        throw new RuntimeException("El CVU proporcionado es inválido.");
	    }

	    if (!cuentas.containsKey(cvu)) {
	        throw new RuntimeException("La cuenta con CVU " + cvu + " no existe.");
	    }

	    Cuenta cuenta = cuentas.get(cvu);

	    return cuenta.getSaldo();
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
		actividades.add(transferencia);
	}

	@Override
	public int realizarInversionRentaFija(String dniUsuario, String cvuCuenta, double monto, int plazo) {

		if (!usuarios.containsKey(dniUsuario)) {
			throw new IllegalArgumentException("Usuario inexistente.");
		}

		Cuenta cuenta = cuentas.get(cvuCuenta);

		if (cuenta == null) {
			throw new IllegalArgumentException("Cuenta inexistente.");
		}

		// crear inversión
		Inversion inversion = new RentaFija(monto, cuenta, actividades.size() + 1, plazo, "ARS", 0.20, true);

		// ejecutar inversión
		inversion.ejecutar();

		// guardar en historial global
		actividades.add(inversion);

		return inversion.getIdInversion();
	}

	@Override
	public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa,
			double tasa) {

		// validar usuario
		if (!usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario no existe.");
		}

		// buscar cuenta
		Cuenta cuenta = cuentas.get(cvu);

		if (cuenta == null) {
			throw new IllegalArgumentException("La cuenta no existe.");
		}

		// generar ID
		int id = actividades.size() + 1;

		// crear inversión
		Inversion inversion = new Divisa(monto, cuenta, id, plazoDias, divisa, tasa, true);

		// ejecutar inversión
		inversion.ejecutar();

		// registrar en historial global
		actividades.add(inversion);

		return inversion.getIdInversion();
	}

	@Override
	public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {

		// validar usuario
		if (!usuarios.containsKey(dni)) {
			throw new IllegalArgumentException("El usuario no existe.");
		}

		// buscar cuenta
		Cuenta cuenta = cuentas.get(cvu);

		if (cuenta == null) {
			throw new IllegalArgumentException("La cuenta no existe.");
		}

		// validar tipo de cuenta
		if (!(cuenta instanceof CuentaCorporativa)) {
			throw new IllegalArgumentException("Solo las cuentas corporativas pueden invertir en fondos de liquidez.");
		}

		// generar ID
		int id = actividades.size() + 1;

		// crear inversión
		Inversion inversion = new FondoLiquidez(monto, cuenta, id, plazoDias);

		// ejecutar inversión
		inversion.ejecutar();

		// registrar en historial global
		actividades.add(inversion);

		return inversion.getIdInversion();
	}

	@Override
	public void precancelarInversion(String dni, String cvu, int idInversion) { // punto 13
	    if (dni == null || cvu == null || dni.isEmpty() || cvu.isEmpty()) {
	        throw new RuntimeException("Los datos de identificación no pueden ser nulos o vacíos.");
	    }

	    if (!usuarios.containsKey(dni)) {
	        throw new RuntimeException("El usuario con DNI " + dni + " no existe.");
	    }

	    if (!cuentas.containsKey(cvu)) {
	        throw new RuntimeException("La cuenta con CVU " + cvu + " no existe.");
	    }

	    Cuenta cuenta = cuentas.get(cvu);

	    List<Actividad> actividades = cuenta.getActividades();
	    
	    int i = 0;
	    boolean encontrada = false;
	    while (i < actividades.size() && !encontrada) {
	        Actividad act = actividades.get(i);

	        if (act instanceof Inversion) {
	            Inversion inv = (Inversion) act;

	            if (inv.getIdInversion() == idInversion) {
	                encontrada = true;
	                // verificación de seguridad
	                if (!inv.getOrigen().getTitular().getDni().equals(dni)) {
	                    throw new RuntimeException("La inversión no pertenece al usuario indicado.");
	                }

	                // Lanza error si la inversión no está activa o no es precancelable [4]
	                if (!inv.estaPrecancelada()) {
	                    throw new RuntimeException("La inversión con ID " + idInversion + " ya fue precancelada.");
	                }

	                if (!inv.getPrecancelable()) {
	                    throw new RuntimeException("Este tipo de inversión no admite precancelación anticipada.");
	                }

	                inv.precancelar();
	            }
	        }
	        
	        if (!encontrada) {
	            i++;
	        }
	    }

	    if (!encontrada) {
	        throw new RuntimeException("No se encontró la inversión con ID " + idInversion + " en la cuenta especificada.");
	    }
	}

	@Override
	public String consultarCvu(String alias) {

		if (!aliasToCvu.containsKey(alias)) {
			throw new IllegalArgumentException("El alias no está registrado.");
		}

		return aliasToCvu.get(alias);
	}

	@Override
	public List<String> consultarHistorialGlobal() {

		List<String> historial = new ArrayList<>();

		for (Actividad act : actividades) {
			historial.add(act.toString());
		}

		return historial;
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
	public List<String> cuentasConMayorVolumen(int cantidadTop) {

		if (cantidadTop <= 0) {
			throw new IllegalArgumentException("La cantidad debe ser positiva.");
		}

		// convertir map de cuentas en lista
		List<Cuenta> listaCuentas = new ArrayList<>(cuentas.values());

		for (Cuenta c : listaCuentas) {
			System.out.println(c.getCvu() + " -> " + c.getCantOperaciones());
		}

		// ordenar de mayor a menor cantidad de operaciones
		listaCuentas.sort((c1, c2) -> Integer.compare(c2.getCantOperaciones(), c1.getCantOperaciones()));

		System.out.println("ORDENADO:");

		for (Cuenta c : listaCuentas) {
			System.out.println(c.getCvu() + " -> " + c.getCantOperaciones());
		}

		List<String> resultado = new ArrayList<>();

		// tomar top N
		for (int i = 0; i < cantidadTop && i < listaCuentas.size(); i++) {

			resultado.add(listaCuentas.get(i).toString());
		}

		return resultado;
	}

	// Se debe poder imprimir “Billetera” mostrando sus datos en formato adecuado
	// para poder comprenderlo.
	@Override
	public String toString() {

		return null;
	}
}
