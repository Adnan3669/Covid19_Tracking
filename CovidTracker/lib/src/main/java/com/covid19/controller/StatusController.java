package com.covid19.controller;

import java.time.LocalDate;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.service.UserService;
/*@RestController annotation to create RESTful web service.
 * 
 */
@RestController
@RequestMapping("status")
public class StatusController {
	@Autowired
	UserService service;
	
	
	Logger logger = LoggerFactory.getLogger(StatusController.class);

	/*
	 * Creating a query to fetch total cases from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	// http://localhost:9090/CovidTracker.com/status/totalcases
	@GetMapping(path = "/totalcases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCases()

	{
		logger.info("For getting details of total cases of COIVD19");
		return new ResponseEntity<Integer>(service.findTotalCases(), HttpStatus.ACCEPTED);
	}

	/*
	 * Creating a query to fetch total cases in 24 hrs from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	// http://localhost:9090/CovidTracker.com/status/totalcasesIn24Hrs
	@GetMapping(path = "/totalcasesIn24Hrs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCasesIn24Hrs()

	{
		logger.info("For getting details of total cases in last 24 hours");

		return new ResponseEntity<Integer>(service.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1)),
				HttpStatus.ACCEPTED);
	}

	/*
	 * Creating a query to fetch total lab cases from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	// http://localhost:9090/CovidTracker.com/status/totalLabCases
	@GetMapping(path = "/totalLabCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalLabTest()

	{
		logger.info("for getting details of total LAB TESTS of COIVD19");

		return new ResponseEntity<Integer>(service.findTotalLabTest(), HttpStatus.ACCEPTED);
	}

	/*
	 * Creating a query to fetch total recovered cases from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	// http://localhost:9090/CovidTracker.com/status/totalRecoverCases
	@GetMapping(path = "/totalRecoverCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalRecoverdCases()

	{
		logger.info("For getting details of total recovered cases");

		return new ResponseEntity<Integer>(service.findTotalRecoveredCases(), HttpStatus.ACCEPTED);
	}
	
	/*
	 * Creating a query to fetch total patients in isolation from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	// http://localhost:9090/CovidTracker.com/status/totalPatientInIsolation
	@GetMapping(path = "/totalPatientInIsolation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalPatientInIsolation()

	{
		logger.info("For getting details of patients who are in ISOLATION");

		return new ResponseEntity<Integer>(service.findTotalPatientInIsolation(), HttpStatus.ACCEPTED);
	}
	
	/*
	 * Creating a query to fetch total death from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	// http://localhost:9090/CovidTracker.com/status/totalDeath
	@GetMapping(path = "/totalDeath", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalDeath()

	{
		logger.info("For getting details of total DEATHS due to COVID19");

		return new ResponseEntity<Integer>(service.findTotalDeath(), HttpStatus.ACCEPTED);
	}
	
	
	/*
	 * Creating a query to fetch total cases based on zone and date from postman
	 * @GetMapping is use to handle GET type of request 
	 */
	//http://localhost:9090/CovidTracker.com/status/totalDataBasedOnZoneAndDate?zoneName=pune&date=2021-05-01
    @GetMapping(path = "/totalDataBasedOnZoneAndDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findTotalDataBasedOnZoneAndDate(@RequestParam("zoneName") @Pattern(regexp = "[A-Za-z0-9]+$") @NotNull String zoneName,@RequestParam("date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date)

    {
        logger.info("For getting details of total Data Based On Zone And Date");

        return new ResponseEntity<List<String>>(service.findTotalDataBasedOnZoneAndDate(zoneName,date),HttpStatus.ACCEPTED);
    }

}