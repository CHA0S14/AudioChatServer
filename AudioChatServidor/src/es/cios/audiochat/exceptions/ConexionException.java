package es.cios.audiochat.exceptions;

@SuppressWarnings("serial")
public class ConexionException extends RuntimeException{

	public ConexionException(String message, Exception cause) {
		super(message, cause);
	}

	public ConexionException(String message) {
		super(message);
	}

}
