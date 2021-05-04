package com.covid19.controller;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.DateIsNotAppropriate;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchMonthException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.exceptions.NoSuchStatusException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;

@RestController
@ControllerAdvice
class ExceptionController extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = {NoSuchAdminException.class,NoSuchHospitalException.class,DateIsNotAppropriate.class,
			NoSuchMonthException.class,NoSuchPatientException.class,NoSuchStatusException.class,NoSuchTypeException.class,
			NoSuchZoneException.class,AdminException.class})
	public ResponseEntity<String> exceptionHandler( Exception e) {
		logger.error("For throwing error on "+e.getCause());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex,
	        HttpHeaders headers,
	        HttpStatus status,
	        WebRequest request) {
		 String errorMessage = ex.getMessage();
	        return new ResponseEntity(errorMessage, headers, status);
	    }
}