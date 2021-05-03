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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.service.HospitalService;

@Validated
@RestController
@RequestMapping(path = "hospital")
public class HospitalController {
	@Autowired
	private HospitalService hospitalService;
	
	Logger logger = LoggerFactory.getLogger(HospitalController.class);
	// http://localhost:9090/CovidTracker.com/hospital
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hospital>> getAllHospitals() {
		logger.info("For getting details of ALL HOSPITALS");
		ResponseEntity<List<Hospital>> response = null;
		List<Hospital> result = hospitalService.findAllHospitals();
		if (result != null)
			response = new ResponseEntity<>(result, HttpStatus.FOUND);
		
		else
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
		logger.info("For getting details of HOSPITALS by zone");
		return hospitalService.findHospitalByZone(zoneName);
	}



	// http://localhost:9090/CovidTracker.com/hospital/byFreeBeds
	@GetMapping(path = "/byFreeBeds",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hospital>> getHospitalByFreeBeds() {
		logger.info("For getting details of availaible free beds in HOSPITAL");
		ResponseEntity<List<Hospital>> response = null;
		List<Hospital> result = hospitalService.findHospitalByFreeBeds();
		if (result != null)
			response = new ResponseEntity<>(result, HttpStatus.FOUND);
		else
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return response;

	}
	// http://localhost:9090/CovidTracker.com/hospital/addHospitalZone
	@PostMapping(path = "/addHospitalZone")
	public HospitalZone addHospitalZone(@RequestBody @Valid HospitalZone zone) throws NoSuchZoneException {
		logger.info("for getting details of HOSPITALS by its zone");
		return  hospitalService.addHospitalZone(zone);
	}
	// http://localhost:9090/CovidTracker.com/hospital/addHospitaltype
	@PostMapping(path = "/addHospitalType")
	public HospitalType addHospitalType(@RequestBody @Valid HospitalType type) throws NoSuchZoneException {
		logger.info("for getting details of HOSPITALS by its zone");
		return  hospitalService.addHospitalType(type);
	}
	// http://localhost:9090/CovidTracker.com/hospital/allHospitalZone
	@GetMapping(path = "/allHospitalZone")
	public List<HospitalZone> getAllHospitalZone() {
		logger.info("for getting details of HOSPITALS by its zone");
		return  hospitalService.getAllHospitalZones();
	}
	// http://localhost:9090/CovidTracker.com/hospital/allHospitalType
	@GetMapping(path = "/allHospitalType")
	public List<HospitalType> getAllHospitalType() {
		logger.info("for getting details of HOSPITALS by its zone");
		return  hospitalService.getAllHospitalType();
	}

}