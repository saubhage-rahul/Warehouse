package com.app.warehouse.Exception;

public class PurchaseOrderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PurchaseOrderException() {
		super();
	}

	public PurchaseOrderException(String message) {
		super(message);
	}

}
