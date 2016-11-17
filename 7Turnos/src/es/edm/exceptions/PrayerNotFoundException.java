package es.edm.exceptions;

public class PrayerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2546719797482159682L;

	public PrayerNotFoundException() {
		super();
	}

	public PrayerNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrayerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrayerNotFoundException(String message) {
		super(message);
	}

	public PrayerNotFoundException(Throwable cause) {
		super(cause);
	}

}
