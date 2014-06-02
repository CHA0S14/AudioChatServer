package es.cios.audiochat.exceptions;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class ConexionException extends RuntimeException{

	/**
	 * @param message
	 * @param cause
	 */
	public ConexionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ConexionException(String message) {
		super(message);
	}
}
