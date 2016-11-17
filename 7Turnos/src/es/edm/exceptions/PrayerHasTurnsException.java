package es.edm.exceptions;

public class PrayerHasTurnsException extends Exception {


	private static final long serialVersionUID = 7188605626375934438L;

	public PrayerHasTurnsException() {
	}

	public PrayerHasTurnsException(String message) {
		super(message);
	}

	public PrayerHasTurnsException(Throwable cause) {
		super(cause);
	}

	public PrayerHasTurnsException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrayerHasTurnsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
