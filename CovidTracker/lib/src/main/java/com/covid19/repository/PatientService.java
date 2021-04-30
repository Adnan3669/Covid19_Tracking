package com.covid19.repository;

import com.covid19.model.Patient;
import com.covid19.model.Result;
import com.covid19.model.Status;
import com.covid19.model.CovidTest;

public interface PatientService {
	public Patient addPatient(int hospitalId, Patient patient);

	public Patient modifyPatient(Patient patient);

	public CovidTest addPatientTestDetails(int patientId, CovidTest covidTest);

	public Status addPatientStatus(int patientId, Status status);

	public Status modifyPatientStatus(Status status);

	public Result addResult(Result result);

}