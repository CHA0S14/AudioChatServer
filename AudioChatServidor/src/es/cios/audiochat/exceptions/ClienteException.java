package es.cios.audiochat.exceptions;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class ClienteException extends RuntimeException{

	/**
	 * @param message
	 * @param cause
	 */
	public ClienteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ClienteException(String message) {
		super(message);
	}
}
