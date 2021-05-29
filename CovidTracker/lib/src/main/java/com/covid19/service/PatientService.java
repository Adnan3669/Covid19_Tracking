package com.covid19.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.covid19.entities.CovidTest;
import com.covid19.entities.Patient;
import com.covid19.entities.Status;
import com.covid19.exceptions.DateIsNotAppropriate;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.exceptions.NoSuchStatusException;

public interface PatientService {
	public Patient addPatient(@Positive int hospitalId, @Valid Patient patient) throws NoSuchHospitalException;

	public Patient modifyPatient(@Valid Patient patient) throws NoSuchPatientException;

	public Status addPatientTestDetails(@Positive int patientId,@Valid CovidTest covidTest) throws NoSuchPatientException, DateIsNotAppropriate, NoSuchStatusException;

	public Status addPatientStatus(@Positive int patientId,@Valid Status status) throws NoSuchPatientException, DateIsNotAppropriate;

	public Status modifyPatientStatus(@Valid Status status) throws NoSuchStatusException, DateIsNotAppropriate;

	List<Patient> findAllPatients();
}