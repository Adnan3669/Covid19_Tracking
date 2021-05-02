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

@Validated
@ControllerAdvice
@RestController
class ExceptionController extends ResponseEntityExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(NoSuchAdminException e) {
		logger.error("For verifying if the ADMIN is present or not");
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST); 
	
	}

	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(NoSuchHospitalException e) {
		logger.error("For verifying if the HOSPITAL is present or not");
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST); 
	}

	@ExceptionHandler(ConstraintViolationException.class)
	private ResponseEntity<List<String>> handleConstraintViolation(ConstraintViolationException cve) {
		Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();
		List<String> violationList = new ArrayList<>();
		for (ConstraintViolation<?> cv : cvs) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("\nViolation: " + cv.getMessage() + "\n" + "Entity: " + cv.getRootBeanClass().getSimpleName());
			// The violation occurred on a leaf bean
			if (cv.getLeafBean() != null && cv.getRootBean() != cv.getLeafBean()) {
				buffer.append("\nEmbeddable: " + cv.getLeafBean().getClass().getSimpleName());
			}
			buffer.append("\nAttribute: " + cv.getPropertyPath());
			buffer.append("\nInvalid value: " + cv.getInvalidValue());
			violationList.add(buffer.toString());
		}
		return new ResponseEntity<>(violationList,HttpStatus.BAD_REQUEST);
	}

}
