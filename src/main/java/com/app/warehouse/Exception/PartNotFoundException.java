package com.app.warehouse.Exception;

public class PartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PartNotFoundException() {
		super();
	}

	public PartNotFoundException(String message) {
		super(message);
	}

}
