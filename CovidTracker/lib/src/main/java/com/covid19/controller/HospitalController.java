package com.covid19.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.service.HospitalService;

@Validated
@RestController
@RequestMapping(path = "hospital")
public class HospitalController {
	@Autowired
	private HospitalService hospitalService;
	
	Logger logger = LoggerFactory.getLogger(HospitalController.class);

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hospital>> getAllHospitals() {
		logger.info("For getting details of ALL HOSPITALS");
		ResponseEntity<List<Hospital>> response = null;
		List<Hospital> result = hospitalService.findAllHospitals();
		if (result != null)
			response = new ResponseEntity<List<Hospital>>(result, HttpStatus.FOUND);
		
		else
			response = new ResponseEntity<List<Hospital>>(HttpStatus.NOT_FOUND);
		return response;
	}

	// http://localhost:9090/CovidTracker.com/hospital/byType/Goverment
	@GetMapping(path = "/byType/{typeName}")
	public List<Hospital> getHospitalByType(@PathVariable("typeName") String typeName) throws NoSuchTypeException {
		logger.info("For getting details of HOSPITALS by its type");
		return hospitalService.findHospitalByType(typeName);
	}

	// http://localhost:9090/CovidTracker.com/hospital/byType/Dahisar
	@GetMapping(path = "/byZone/{zoneName}")
	public List<Hospital> getHospitalByZone(@PathVariable("zoneName") String zoneName) throws NoSuchZoneException {
		logger.info("for getting details of HOSPITALS by its zone");
		return hospitalService.findHospitalByZone(zoneName);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Hospital> modifyHospital(@RequestBody @Valid Hospital hospital) throws NoSuchHospitalException {
		logger.info("For modifying HOSPITAL details");
		ResponseEntity<Hospital> response = null;
		Hospital result = hospitalService.modifyHospital(hospital);
		if (result != null)
			response = new ResponseEntity<Hospital>(result, HttpStatus.OK);
		else
			response = new ResponseEntity<Hospital>(HttpStatus.BAD_REQUEST);
		return response;
	}

	@GetMapping(path = "/byFreeBeds")
	public ResponseEntity<List<Hospital>> getHospitalByFreeBeds() {
		logger.info("For getting details of availaible free beds in HOSPITAL");
		ResponseEntity<List<Hospital>> response = null;
		List<Hospital> result = hospitalService.findHospitalByFreeBeds();
		if (result != null)
			response = new ResponseEntity<List<Hospital>>(result, HttpStatus.FOUND);
		else
			response = new ResponseEntity<List<Hospital>>(HttpStatus.NOT_FOUND);
		return response;

	}

	@ExceptionHandler(NoSuchZoneException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Zone not found")
	public void handleException() {
		logger.error("For verifying if the Zone is present or not");
	}

	@ExceptionHandler(NoSuchTypeException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Type not found")
	public void handleException1() {
		logger.error("For verifying if the Type is present or not");
	}
}