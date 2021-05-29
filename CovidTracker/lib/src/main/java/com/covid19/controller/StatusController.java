package com.covid19.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.entities.Record;
import com.covid19.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("status")
@CrossOrigin
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

	//http://localhost:9090/CovidTracker.com/status/totalcasesIn24Hrs
	@GetMapping(path = "/totalcasesIn24Hrs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCasesIn24Hrs()

	{
		logger.info("For getting details of total cases in last 24 hours");

		return new ResponseEntity<Integer>(service.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1)),
				HttpStatus.ACCEPTED);
	}

	//http://localhost:9090/CovidTracker.com/status/totalLabCases
	@GetMapping(path = "/totalLabCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalLabTest()

	{
		logger.info("for getting details of total LAB TESTS of COIVD19");

		return new ResponseEntity<Integer>(service.findTotalLabTest(), HttpStatus.ACCEPTED);
	}

	//http://localhost:9090/CovidTracker.com/status/totalRecoverCases
	@GetMapping(path = "/totalRecoverCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalRecoverdCases()

	{
		logger.info("For getting details of total recovered cases");

		return new ResponseEntity<Integer>(service.findTotalRecoveredCases(), HttpStatus.ACCEPTED);
	}

	//http://localhost:9090/CovidTracker.com/status/totalPatientInIsolation
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

		return new ResponseEntity<>(service.findTotalDeath(), HttpStatus.ACCEPTED);
	}
	
	//http://localhost:9090/CovidTracker.com/status/totalDataBasedOnZoneAndDate?zoneName=pune&date=2021-05-01
	@GetMapping(path = "/totalDataBasedOnZoneAndDate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Record> findTotalDataBasedOnZoneAndDate(@RequestParam("zoneId")  @Positive int zoneId,@RequestParam("date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date)

	{
		logger.info("For getting details of total Data Based On Zone And Date");

		return new ResponseEntity<>(service.findTotalDataBasedOnZoneAndDate(zoneId,date),HttpStatus.ACCEPTED);
	}
	@GetMapping(path = "/totalDataBasedOnMonth", produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Map<Integer,Record>> findTotalDataBasedOnMonth(@RequestParam("startMonth")  @Min(1) @Max(12) int startMonth,@RequestParam("endMonth") @Min(1) @Max(12) int endMonth)

	{
		logger.info("For getting details of findTotalDataBasedOnMonth");

		return new ResponseEntity<>(service.findTotalDataBasedOnMonth(startMonth,endMonth),HttpStatus.ACCEPTED);
	}
	@GetMapping(path = "/totalDataBasedOnZone", produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Map<String,Record>> findTotalDataBasedOnZone()

	{
		logger.info("For getting details of findTotalDataBasedOnMonth");

		return new ResponseEntity<>(service.findTotalDataBasedOnZone(),HttpStatus.ACCEPTED);
	}

}