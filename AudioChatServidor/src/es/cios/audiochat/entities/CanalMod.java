package es.cios.audiochat.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CanalMod implements Serializable{
	private int canal = -1;
	private int subCanal = -1;
	private String nombre;
	private boolean nuevo = false;

	/**
	 * @param canal
	 * @param subCanal
	 * @param nombre
	 */
	public CanalMod(int canal, String nombre) {
		super();
		this.canal = canal;
		this.nombre = nombre;
	}

	/**
	 * @param canal
	 * @param subCanal
	 * @param nombre
	 * @param nuevo
	 */
	public CanalMod(int canal, int subCanal, String nombre, boolean nuevo) {
		super();
		this.canal = canal;
		this.subCanal = subCanal;
		this.nombre = nombre;
		this.nuevo = nuevo;
	}

	/**
	 * @return the canal
	 */
	public int getCanal() {
		return canal;
	}

	/**
	 * @param canal the canal to set
	 */
	public void setCanal(int canal) {
		this.canal = canal;
	}

	/**
	 * @return the subCanal
	 */
	public int getSubCanal() {
		return subCanal;
	}

	/**
	 * @param subCanal the subCanal to set
	 */
	public void setSubCanal(int subCanal) {
		this.subCanal = subCanal;
	}

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

	/**
	 * @return the nuevo
	 */
	public boolean isNuevo() {
		return nuevo;
	}

	/**
	 * @param nuevo the nuevo to set
	 */
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}
	
	
}
