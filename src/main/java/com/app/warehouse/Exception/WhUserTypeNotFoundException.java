package com.app.warehouse.Exception;

public class WhUserTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WhUserTypeNotFoundException() {
		super();
	}

	public WhUserTypeNotFoundException(String message) {
		super(message);
	}

}
