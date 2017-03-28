package es.edm.exceptions;

public class DDBBException extends RuntimeException {


    private static final long serialVersionUID = -6755166506829496528L;

    public DDBBException() {
        super();
    }

    public DDBBException(String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DDBBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DDBBException(String message) {
        super(message);
    }

    public DDBBException(Throwable cause) {
        super(cause);
    }

}
