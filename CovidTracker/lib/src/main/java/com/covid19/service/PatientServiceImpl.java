package com.covid19.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid19.model.Hospital;
import com.covid19.model.Patient;
import com.covid19.model.Result;
import com.covid19.model.Status;
import com.covid19.model.CovidTest;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.PatientRepository;
import com.covid19.repository.ResultRepository;
import com.covid19.repository.StatusRepository;
import com.covid19.repository.PatientTestRepository;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private HospitalRepository hospitalRepository;
	@Autowired
	private PatientTestRepository patientTestRepository;
	@Autowired
	private ResultRepository resultRepository;
	@Autowired
	private StatusRepository statusRepository;
	
	@Override
	public Patient addPatient(int hospitalId, Patient patient) {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		patient.setHospital(hospital);
		patientRepository.save(patient);
		return patient;
	}

	@Override
	public Patient modifyPatient(Patient patient) {
		Patient modifypatient = patientRepository.findByPatientId(patient.getPatientId());
		if(!modifypatient.getPatientFirstName().equals(patient.getPatientFirstName())) {
			modifypatient.setPatientFirstName(patient.getPatientFirstName());
		}
		return patientRepository.save(modifypatient);
	}

	@Override
	public CovidTest addPatientTestDetails(int patientId, CovidTest covidTest) {
		Patient patient = patientRepository.findByPatientId(patientId);
		covidTest.setPatient(patient);
		patientTestRepository.save(covidTest);
		return covidTest;
	}
	
	@Override
	public Status addPatientStatus(int patientId, Status status) {
		Patient patient = patientRepository.findByPatientId(patientId);
		status.setPatient(patient);
		statusRepository.save(status);
		return status;
	}

	@Override
	public Status modifyPatientStatus(Status status) {
		return statusRepository.save(status);
	}

	@Override
	public Result addResult(Result result) {
		return resultRepository.save(result);
	}
}