package com.app.warehouse.Exception;

public class ShipingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ShipingNotFoundException() {
		super();
	}

	public ShipingNotFoundException(String message) {
		super(message);
	}

}
