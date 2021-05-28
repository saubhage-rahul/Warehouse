package com.app.warehouse.Exception;

public class OrderMethodNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderMethodNotFoundException() {
		super();
	}

	public OrderMethodNotFoundException(String message) {
		super(message);
	}

}
