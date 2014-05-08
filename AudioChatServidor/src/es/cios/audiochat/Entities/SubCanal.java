package es.cios.audiochat.Entities;

import es.cios.audiochat.exceptions.SubCanalException;

public class SubCanal extends Canal{	
	@Override
	public void addSubCanal(SubCanal subCanal) throws SubCanalException{
		throw new SubCanalException("No se puede añadir un sub canal a un sub canal");
	}
}
