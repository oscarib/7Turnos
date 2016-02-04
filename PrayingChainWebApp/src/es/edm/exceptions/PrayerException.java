package es.edm.exceptions;

public class PrayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1132412415230965021L;

	/**
	 * 
	 */
	public PrayerException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PrayerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PrayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public PrayerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PrayerException(Throwable cause) {
		super(cause);
	}

}
