package com.app.warehouse.Exception;

public class DocumentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocumentNotFoundException() {
		super();
	}

	public DocumentNotFoundException(String message) {
		super(message);
	}

}
