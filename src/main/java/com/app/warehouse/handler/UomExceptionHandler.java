package com.app.warehouse.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.warehouse.Exception.UomNotFoundException;
import com.app.warehouse.error.ErrorType;

@RestControllerAdvice
public class UomExceptionHandler {

	@ExceptionHandler(UomNotFoundException.class)
	public ResponseEntity<ErrorType> handleUomNotFoundException(UomNotFoundException unfe) {
		return new ResponseEntity<ErrorType>(new ErrorType(new Date().toString(), "Uom", unfe.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
