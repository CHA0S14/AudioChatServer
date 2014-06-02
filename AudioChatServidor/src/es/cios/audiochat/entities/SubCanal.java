package es.cios.audiochat.entities;

import es.cios.audiochat.exceptions.SubCanalException;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class SubCanal extends Canal{	
	
	
	
	/**
	 * @see es.cios.audiochat.entities.Canal#addSubCanal(es.cios.audiochat.entities.SubCanal)
	 */
	@Override
	public void addSubCanal(SubCanal subCanal) throws SubCanalException{
		throw new SubCanalException("No se puede añadir un sub canal a un sub canal");
	}
	
	/**
	 * @see es.cios.audiochat.entities.Canal#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name="SC: " + name;
	}	
}
