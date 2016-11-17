package es.edm.exceptions;

public class DataTimeException extends Exception {

	private static final long serialVersionUID = -8819351513707245161L;

	public DataTimeException() {
	}

	public DataTimeException(String message) {
		super(message);
	}

	public DataTimeException(Throwable cause) {
		super(cause);
	}

	public DataTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
