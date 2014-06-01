package es.cios.audiochat.entities;

import es.cios.audiochat.exceptions.SubCanalException;

@SuppressWarnings("serial")
public class SubCanal extends Canal{	
	@Override
	public void addSubCanal(SubCanal subCanal) throws SubCanalException{
		throw new SubCanalException("No se puede a�adir un sub canal a un sub canal");
	}
	
	/**
	 * @see es.cios.audiochat.entities.Canal#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name="SC: " + name;
	}	
}
