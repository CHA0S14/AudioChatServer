package es.cios.audiochat.exceptions;

@SuppressWarnings("serial")
public class ClienteException extends RuntimeException{
	public ClienteException(String mensaje) {
		super(mensaje);
	}
}
