package es.edm.exceptions;

public class EmptyParameterException extends RuntimeException {

	private static final long serialVersionUID = 812652056266048477L;

	public EmptyParameterException() {
	}

	public EmptyParameterException(String message) {
		super(message);
	}

	public EmptyParameterException(Throwable cause) {
		super(cause);
	}

	public EmptyParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
