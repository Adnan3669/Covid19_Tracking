package com.covid19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.model.Patient;
import com.covid19.model.Result;
import com.covid19.model.Status;
import com.covid19.model.CovidTest;
import com.covid19.service.PatientService;

@RestController
@RequestMapping(path = "/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	// http://localhost:9090/CovidTracker.com/patients/addpatient
	@PostMapping(path = "/addpatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Patient addPatient(@RequestParam("hospitalId") int hospitalId, @RequestBody Patient patient) {
		return patientService.addPatient(hospitalId, patient);
	}

	// http://localhost:9090/CovidTracker.com/patients/modifypatient
	@PutMapping(path = "/modifypatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Patient modifyPatient(@RequestBody Patient patient) {
		return patientService.modifyPatient(patient);
	}

	// http://localhost:9090/CovidTracker.com/patients/addpatienttestdetails
	@PostMapping(path = "/addpatienttestdetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public CovidTest addPatientTestDetails(@RequestParam("patientId") int patientId, @RequestBody CovidTest covidTest) {
		return patientService.addPatientTestDetails(patientId, covidTest);
	}

	// http://localhost:9090/CovidTracker.com/patients/addpatientstatus
	@PostMapping(path = "/addpatientstatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Status addPatientStatus(@RequestParam("patientId") int patientId, @RequestBody Status status) {
		return patientService.addPatientStatus(patientId, status);
	}

	// http://localhost:9090/CovidTracker.com/patients/modifypatientstatus
	@PutMapping(path = "/modifypatientstatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Status modifyPatientStatus(@RequestBody Status status) {
		return patientService.modifyPatientStatus(status);
	}

	// http://localhost:9090/CovidTracker.com/patients/addpatientresult
	@PostMapping(path = "/addpatientresult", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Result addResult(@RequestBody Result result) {
		return patientService.addResult(result);
	}
}