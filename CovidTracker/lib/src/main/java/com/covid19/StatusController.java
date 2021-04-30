package com.covid19;

import java.time.LocalDate;

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

	// http://localhost:9090/CovidTracker.com/status/totalcases
	@GetMapping(path = "/totalcases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCases()

	{
		return new ResponseEntity<Integer>(service.findTotalCases(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalcasesIn24Hrs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalCasesIn24Hrs()

	{
		return new ResponseEntity<Integer>(service.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1)),
				HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalLabCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalLabTest()

	{
		return new ResponseEntity<Integer>(service.findTotalLabTest(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalRecoverCases", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalRecoverdCases()

	{
		return new ResponseEntity<Integer>(service.findTotalRecoveredCases(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalPatientInIsolation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalPatientInIsolation()

	{
		return new ResponseEntity<Integer>(service.findTotalPatientInIsolation(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/totalDeath", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findTotalDeath()

	{
		return new ResponseEntity<Integer>(service.findTotalDeath(), HttpStatus.ACCEPTED);
	}

}