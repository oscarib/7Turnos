package es.edm.exceptions;

public class TurnAlreadyExistsException extends RuntimeException {

	public TurnAlreadyExistsException() {
		super();
	}

	public TurnAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TurnAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public TurnAlreadyExistsException(String message) {
		super(message);
	}

	public TurnAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 275099808120377371L;

}
