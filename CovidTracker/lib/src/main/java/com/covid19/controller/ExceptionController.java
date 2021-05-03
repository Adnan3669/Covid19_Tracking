package com.covid19.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;

@RestController
@ControllerAdvice
class ExceptionController extends ResponseEntityExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(NoSuchAdminException e) {
		logger.error("For verifying if the ADMIN is present or not");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(NoSuchHospitalException e) {
		logger.error("For verifying if the HOSPITAL is present or not");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(NoSuchTypeException e) {
		logger.error("For verifying if the Hospital Type is present or not");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(NoSuchZoneException e) {
		logger.error("For verifying if the Hospital Zone is present or not");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	

}
