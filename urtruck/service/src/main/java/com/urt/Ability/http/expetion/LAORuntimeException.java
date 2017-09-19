package com.urt.Ability.http.expetion;

public class LAORuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LAORuntimeException() {
		super();
	}

	public LAORuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public LAORuntimeException(String message) {
		super(message);
	}

	public LAORuntimeException(Throwable cause) {
		super(cause);
	}
}
