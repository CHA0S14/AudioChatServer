package es.cios.audiochat.entities;

import java.io.Serializable;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class Nombre implements Serializable{
	private String nombre;

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
