package com.app.warehouse.error;

import lombok.Data;

@Data
public class ErrorType {
	private String dateTime;
	private String module;
	private String message;

	public ErrorType() {

	}

	public ErrorType(String dateTime, String module, String message) {
		this.dateTime = dateTime;
		this.module = module;
		this.message = message;
	}

}
