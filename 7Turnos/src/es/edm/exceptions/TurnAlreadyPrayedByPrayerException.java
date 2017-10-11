package es.edm.exceptions;

public class TurnAlreadyPrayedByPrayerException extends Exception {

    private static final long serialVersionUID = -6426954488854065700L;

    public TurnAlreadyPrayedByPrayerException() {
        super();
    }

    public TurnAlreadyPrayedByPrayerException(String message, Throwable cause, boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TurnAlreadyPrayedByPrayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TurnAlreadyPrayedByPrayerException(String message) {
        super(message);
    }

    public TurnAlreadyPrayedByPrayerException(Throwable cause) {
        super(cause);
    }

}
