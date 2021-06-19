package com.app.warehouse.Exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String message) {
		super(message);
	}

}
