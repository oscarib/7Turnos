package es.edm.exceptions;

public class PrayingPlanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5776741062907482201L;

	public PrayingPlanException() {
		super();
	}

	public PrayingPlanException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrayingPlanException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrayingPlanException(String message) {
		super(message);
	}

	public PrayingPlanException(Throwable cause) {
		super(cause);
	}

}
