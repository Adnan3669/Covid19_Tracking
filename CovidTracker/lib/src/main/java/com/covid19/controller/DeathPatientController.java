package com.covid19.controller;

import java.security.NoSuchAlgorithmException;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.security.CryptWithMD5;
import com.covid19.service.DeathPatientService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@Validated
@CrossOrigin
@RequestMapping("deathstatistic")
public class DeathPatientController {

	@Autowired
	DeathPatientService deathService;

	Logger logger = LoggerFactory.getLogger(DeathPatientController.class);

	// http://localhost:9090/CovidTracker.com/deathstatistic/monthWiseDeath/
	@GetMapping(path = "/monthWiseDeath/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalMonthWiseDeath(@PathVariable("month") @RequestBody @Min(1) @Max(12) int month) {
		logger.info("For getting details of total Month wise deaths");

		return new ResponseEntity<>(deathService.findMonthWiseDeath(month), HttpStatus.ACCEPTED);
	}

	// http://localhost:9090/CovidTracker.com/deathstatistic/divisionWiseDeath/Gujarat
	@GetMapping(path = "/divisionWiseDeath/{hospitalZone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalDivisionWiseDeath(@PathVariable("hospitalZone") @RequestBody @NotNull String hospitalZone) {
		logger.info("For getting details of total Division wise deaths");

		return new ResponseEntity<>(deathService.findDivisonWiseDeath(hospitalZone), HttpStatus.ACCEPTED);
	}

	// http://localhost:9090/CovidTracker.com/deathstatistic/ageWiseDeath/21
	@GetMapping(path = "/ageWiseDeath", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalAgeWiseDeath(@RequestParam("fromAge") @Positive int fromAge,@RequestParam("toAge") @Positive int toAge)
	{
		logger.info("For getting details of total age wise deaths");

		return new ResponseEntity<>(deathService.findAgeWiseDeath(fromAge,toAge), HttpStatus.ACCEPTED);
	}

	// http://localhost:9090/CovidTracker.com/deathstatistic/genderWiseDeath/Male
	@GetMapping(path = "/genderWiseDeath/{gender}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> findtotalGenderWiseDeath(@PathVariable("gender") @RequestBody @Pattern(regexp = "^Male?$|^Female?$") String gender) throws NoSuchAlgorithmException
	{
		logger.info("For getting details of total gender wise deaths");
		return new ResponseEntity<>(deathService.findGenderWiseDeath(gender), HttpStatus.ACCEPTED);
	}

}