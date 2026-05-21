package ar.edu.ungs.billetera;

import java.util.HashMap;

public class Usuario {

	private String dni; //antes lo llamabamos "id" pero el interfaz usa "dni"
	private String nombre;
	private String telefono;
	private String email;
	private HashMap<String, Cuenta> cuentas; //cvu : String
	private double totalInvertido;
	
	public Usuario(String dni, String nombre, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.totalInvertido = 0;
        this.cuentas = new HashMap<>();
    }
}
