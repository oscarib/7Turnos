package es.edm.exceptions;

public class MoreThanOnePrayerException extends RuntimeException {


    private static final long serialVersionUID = -5121995054527897994L;

    public MoreThanOnePrayerException() {
        super();
    }

    public MoreThanOnePrayerException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MoreThanOnePrayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoreThanOnePrayerException(String message) {
        super(message);
    }

    public MoreThanOnePrayerException(Throwable cause) {
        super(cause);
    }

}
