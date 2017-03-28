package es.edm.exceptions;

public class DayOfWeekException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5014186564932606248L;

    /**
     *
     */
    public DayOfWeekException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DayOfWeekException(String message, Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public DayOfWeekException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public DayOfWeekException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DayOfWeekException(Throwable cause) {
        super(cause);
    }


}
