package es.edm.exceptions;

public class TurnException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9133268164485745397L;

	public TurnException() {
		super();
	}

	public TurnException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TurnException(String message, Throwable cause) {
		super(message, cause);
	}

	public TurnException(String message) {
		super(message);
	}

	public TurnException(Throwable cause) {
		super(cause);
	}

}
