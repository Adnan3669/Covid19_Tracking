package com.covid19.service;

import com.covid19.model.Patient;
import com.covid19.model.Result;
import com.covid19.model.Status;
import com.covid19.model.Test;

public interface PatientService {
	public Patient addPatient(Patient patient);
	public Patient modifyPatient(Patient patient);
	public Patient addPatientTestDetails(Patient patient);
	public Status modifyPatientStatus(Status status );
	public Result addResult(Result result);
}
