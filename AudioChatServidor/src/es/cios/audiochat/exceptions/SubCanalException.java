package es.cios.audiochat.exceptions;

/**
 * 
 * @author Chaos
 *
 */
@SuppressWarnings("serial")
public class SubCanalException extends RuntimeException{
	/**
	 * @param message
	 * @param cause
	 */
	public SubCanalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SubCanalException(String message) {
		super(message);
	}
}
