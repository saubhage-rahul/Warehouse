package com.app.warehouse.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.warehouse.Exception.OrderMethodNotFoundException;
import com.app.warehouse.error.ErrorType;

@RestControllerAdvice
public class OrderMethodExceptionHandler {

	@ExceptionHandler(OrderMethodNotFoundException.class)
	public ResponseEntity<ErrorType> handleOrderMethodNotFoundException(OrderMethodNotFoundException onfe) {
		return new ResponseEntity<ErrorType>(new ErrorType(new Date().toString(), "Order Method", onfe.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
