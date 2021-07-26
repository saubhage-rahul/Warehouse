package com.app.warehouse.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.warehouse.Exception.ShipmentTypeNotFoundException;
import com.app.warehouse.error.ErrorType;

@RestControllerAdvice
public class ShipmentTypeExceptionHandler {

	@ExceptionHandler(ShipmentTypeNotFoundException.class)
	public ResponseEntity<ErrorType> handleShipmentNotFoundException(ShipmentTypeNotFoundException snfe) {
		return new ResponseEntity<ErrorType>(new ErrorType(new Date().toString(), "ShipmentType", snfe.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
