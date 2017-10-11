package es.edm.exceptions;

public class PrayerAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 5878785448647251324L;

    public PrayerAlreadyExistsException() {
        super();
    }

    public PrayerAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PrayerAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrayerAlreadyExistsException(String message) {
        super(message);
    }

    public PrayerAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
