package com.covid19.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.service.DeathPatientService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@Validated
@RequestMapping("deathstatistic")
public class DeathPatientController {

	@Autowired
	DeathPatientService deathService;

	/*
	 * Using LoggerFactory for creation of logger
	 */

	Logger logger = LoggerFactory.getLogger(DeathPatientController.class);

	/*
	 * Using Mapping
	 */

	// http://localhost:9090/CovidTracker.com/deathstatistic/monthWiseDeath/1
	@GetMapping(path = "/monthWiseDeath/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalMonthWiseDeath(
			@PathVariable("month") @RequestBody @Min(1)  @Max(12) int month) {
		logger.info("For getting details of total Month wise deaths");

		return new ResponseEntity<Integer>(deathService.findMonthWiseDeath(month), HttpStatus.ACCEPTED);
	}

	// http://localhost:9090/CovidTracker.com/deathstatistic/divisionWiseDeath/Gujarat
	@GetMapping(path = "/divisionWiseDeath/{hospitalZone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalDivisionWiseDeath(
			@PathVariable("hospitalZone") @RequestBody @NotNull String hospitalZone) {
		logger.info("For getting details of total Division wise deaths");

		return new ResponseEntity<Integer>(deathService.findDivisonWiseDeath(hospitalZone), HttpStatus.ACCEPTED);
	}

	// http://localhost:9090/CovidTracker.com/deathstatistic/ageWiseDeath/21
	@GetMapping(path = "/ageWiseDeath/{age}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalAgeWiseDeath(@PathVariable("age") @RequestBody @Positive int age) {
		logger.info("For getting details of total age wise deaths");

		return new ResponseEntity<Integer>(deathService.findAgeWiseDeath(age), HttpStatus.ACCEPTED);
	}

	// http://localhost:9090/CovidTracker.com/deathstatistic/genderWiseDeath/Male
	@GetMapping(path = "/genderWiseDeath/{gender}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalGenderWiseDeath(
			@PathVariable("gender") @RequestBody @Pattern(regexp = "^Male?$|^Female?$") String gender) {
		logger.info("For getting details of total gender wise deaths");

		return new ResponseEntity<Integer>(deathService.findGenderWiseDeath(gender), HttpStatus.ACCEPTED);
	}

}