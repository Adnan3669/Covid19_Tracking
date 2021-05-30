package com.covid19.controller;

import java.util.List;

import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.entities.CovidTest;
import com.covid19.entities.Patient;
import com.covid19.entities.Status;
import com.covid19.exceptions.DateIsNotAppropriate;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.exceptions.NoSuchStatusException;
import com.covid19.service.PatientService;
@Validated
@RestController
@CrossOrigin
@RequestMapping(path = "/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	Logger logger = LoggerFactory.getLogger(PatientController.class);
	//http://localhost:9090/CovidTracker.com/patients/allpatients
		@GetMapping(path = "/allpatients", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Patient>> getAllPatients() {
			logger.info("For getting details of ALL Patients.");
			ResponseEntity<List<Patient>> response = null;
			List<Patient> result = patientService.findAllPatients();
			if (result != null)
				response = new ResponseEntity<>(result, HttpStatus.OK);
			
			else
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return response;
		} 
	// http://localhost:9090/CovidTracker.com/patients/addpatient
	@PostMapping(path = "/addpatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Patient addPatient(@RequestParam("hospitalId") @Positive int hospitalId, @RequestBody @Valid Patient patient) throws NoSuchHospitalException {
		logger.info("For adding details of new PATIENT");
		return patientService.addPatient(hospitalId, patient);
	}

	// http://localhost:9090/CovidTracker.com/patients/modifypatient
	@PutMapping(path = "/modifypatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Patient modifyPatient(@RequestBody @Valid Patient patient) throws NoSuchPatientException {
		logger.info("For modifying details of PATIENT");
		return patientService.modifyPatient(patient);
	}

	// http://localhost:9090/CovidTracker.com/patients/addpatienttestdetails
	@PostMapping(path = "/addpatienttestdetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Status addPatientTestDetails(@RequestParam("patientId") @Positive int patientId, @RequestBody @Valid CovidTest covidTest) throws NoSuchPatientException, DateIsNotAppropriate, NoSuchStatusException {
		logger.info("For adding TEST details of PATIENT");
		return patientService.addPatientTestDetails(patientId, covidTest);
	}

	// http://localhost:9090/CovidTracker.com/patients/addpatientstatus
	@PostMapping(path = "/addpatientstatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Status addPatientStatus(@RequestParam("patientId") @Positive int patientId, @RequestBody @Valid Status status) throws NoSuchPatientException, DateIsNotAppropriate {
		logger.info("For adding STATUS details of PATIENT");
		return patientService.addPatientStatus(patientId, status);
	}
	
	// http://localhost:9090/CovidTracker.com/patients/modifypatientstatus
	@PutMapping(path = "/modifypatientstatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Status modifyPatientStatus(@RequestBody @Valid Status status) throws NoSuchStatusException, DateIsNotAppropriate {
		System.out.println(status.getDeathDate());
		logger.info("For modifying details of PATIENT STATUS");
		return patientService.modifyPatientStatus(status);
	}

}
