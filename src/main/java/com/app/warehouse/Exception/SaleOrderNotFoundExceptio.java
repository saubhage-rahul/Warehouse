package com.app.warehouse.Exception;

public class SaleOrderNotFoundExceptio extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SaleOrderNotFoundExceptio() {
		super();
	}

	public SaleOrderNotFoundExceptio(String message) {
		super(message);
	}

}
