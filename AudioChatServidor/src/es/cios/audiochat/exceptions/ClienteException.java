package es.cios.audiochat.exceptions;

@SuppressWarnings("serial")
public class ClienteException extends RuntimeException{
	public ClienteException(String mensaje) {
		super(mensaje);
	}
	
	public ClienteException(String mensaje, Exception e) {
		super(mensaje,e);
	}
}
