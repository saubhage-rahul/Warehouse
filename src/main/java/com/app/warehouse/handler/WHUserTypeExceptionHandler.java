package com.app.warehouse.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.warehouse.Exception.WhUserTypeNotFoundException;
import com.app.warehouse.error.ErrorType;

@RestControllerAdvice
public class WHUserTypeExceptionHandler {

	@ExceptionHandler(WhUserTypeNotFoundException.class)
	public ResponseEntity<ErrorType> handleWhUserTypeNotFoundException(WhUserTypeNotFoundException wnfe) {
		return new ResponseEntity<ErrorType>(new ErrorType(new Date().toString(), "WHUserType", wnfe.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
