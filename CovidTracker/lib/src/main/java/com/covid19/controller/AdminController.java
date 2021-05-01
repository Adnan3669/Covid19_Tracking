package com.covid19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Hospital;
import com.covid19.service.AdminService;
import com.covid19.util.AdminCredentials;

import org.slf4j.LoggerFactory;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;

@RestController
@RequestMapping("admin")
@Validated
public class AdminController {
	@Autowired
	AdminService service;
	@Autowired
	AdminCredentials adminCredentials;
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping(path = "/addHospital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hospital addHospital(@RequestBody @Valid Hospital hospital) throws NoSuchAdminException {
		logger.info("For creation of a new HOSPITAL");
		
		return service.addHospital(adminCredentials.adminCredentialsDetail().getAdminId(),hospital);
	}

	@DeleteMapping(path = "/removeHospital")
	public boolean deleteHospital(@RequestParam(name = "id") @Positive int hospitalId)
			throws NoSuchAdminException, NoSuchHospitalException {
		logger.info("For deleting an existing HOSPITAL");
		return service.removeHospitalById(hospitalId);
	}

	@ExceptionHandler
	public String ExceptionHandler(NoSuchAdminException e) {
		logger.error("For verifying if the ADMIN is present or not");
		return e.getMessage();
	}

	@ExceptionHandler
	public String ExceptionHandler(NoSuchHospitalException e) {
		logger.error("For verifying if the HOSPITAL is present or not");
		return e.getMessage();
	}
	@ExceptionHandler(ConstraintViolationException.class)
	  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
	  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
	    return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	  }

	}
