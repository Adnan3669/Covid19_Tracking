package com.covid19.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.service.UserService;

@RestController
@RequestMapping("status")
public class StatusController {
	@Autowired
	UserService service;
	
	Logger logger = LoggerFactory.getLogger(StatusController.class);


	// http://localhost:9090/CovidTracker.com/status/totalcases
	@GetMapping(path = "/totalcases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCases()

	{
		logger.info("For getting details of total cases of COIVD19");
		return new ResponseEntity<Integer>(service.findTotalCases(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalcasesIn24Hrs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCasesIn24Hrs()

	{
		logger.info("For getting details of total cases in last 24 hours");

		return new ResponseEntity<Integer>(service.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1)),
				HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalLabCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalLabTest()

	{
		logger.info("for getting details of total LAB TESTS of COIVD19");

		return new ResponseEntity<Integer>(service.findTotalLabTest(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalRecoverCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalRecoverdCases()

	{
		logger.info("For getting details of total recovered cases");

		return new ResponseEntity<Integer>(service.findTotalRecoveredCases(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalPatientInIsolation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalPatientInIsolation()

	{
		logger.info("For getting details of patients who are in ISOLATION");

		return new ResponseEntity<Integer>(service.findTotalPatientInIsolation(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalDeath", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalDeath()

	{
		logger.info("For getting details of total DEATHS due to COVID19");

		return new ResponseEntity<Integer>(service.findTotalDeath(), HttpStatus.ACCEPTED);
	}

}