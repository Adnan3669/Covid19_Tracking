package com.covid19.service;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.covid19.exceptions.DateIsNotAppropriate;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.exceptions.NoSuchStatusException;
import com.covid19.model.CovidTest;
import com.covid19.model.Hospital;
import com.covid19.model.Patient;
import com.covid19.model.Status;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.PatientRepository;
import com.covid19.repository.PatientTestRepository;
import com.covid19.repository.StatusRepository;
@Validated
@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private HospitalRepository hospitalRepository;
	@Autowired
	private PatientTestRepository patientTestRepository;

	@Autowired
	private StatusRepository statusRepository;

	Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Override
	public Patient addPatient(@Positive int hospitalId, @Valid Patient patient) throws NoSuchHospitalException {
		logger.info("For adding PATIENT");
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital != null) {
			patient.setHospital(hospital);
			patientRepository.save(patient);
			hospital.getPatients().add(patient);
			hospitalRepository.save(hospital);
			return patient;
		} else {
			throw new NoSuchHospitalException("No Such Hospital Exists");
		}
	}

	@Override
	public Patient modifyPatient(@Valid Patient patient) throws NoSuchPatientException {
		logger.info("For modifying PATIENT");

		Patient modifypatient = patientRepository.findByPatientId(patient.getPatientId());
		if (modifypatient != null) {

			modifypatient.setPatientFirstName(patient.getPatientFirstName());

			modifypatient.setPatientLastName(patient.getPatientLastName());

			modifypatient.setPatientAge(patient.getPatientAge());

			modifypatient.setPatientMobileNo(patient.getPatientMobileNo());

			modifypatient.setPatientGender(patient.getPatientGender());

			return patientRepository.save(modifypatient);
		} else {
			throw new NoSuchPatientException("No Such Patient Exist ");
		}
	}

	@Override
	public CovidTest addPatientTestDetails(@Positive int patientId, @Valid CovidTest covidTest)
			throws NoSuchPatientException {
		logger.info("For adding PATIENT test details");
		Patient patient = patientRepository.findByPatientId(patientId);
		if (patient != null) {
			covidTest.setPatient(patient);
			patientTestRepository.save(covidTest);
			return covidTest;
		} else {
			throw new NoSuchPatientException("No Such Patient Exist");
		}
	}

	@Override
	public Status addPatientStatus(@Positive int patientId, @Valid Status status)
			throws NoSuchPatientException, DateIsNotAppropriate {
		logger.info("For adding PATIENT status");

		Patient patient = patientRepository.findByPatientId(patientId);
		if (patient != null) {
			if (((status.getConfirmDate().isBefore(status.getIsolationDate())
					|| status.getConfirmDate().isEqual(status.getIsolationDate())) && status.getConfirmDate() != null
					&& status.getIsolationDate() != null)
					&& !(status.getRecoveredDate() != null && status.getDeathDate() != null)) {

				status.setConfirmDate(status.getConfirmDate());
				status.setIsolationDate(status.getIsolationDate());
				status.setRecoveredDate(status.getRecoveredDate());
				status.setDeathDate(status.getDeathDate());
				status.setPatient(patient);
				patient.getStatus().add(status);
				return statusRepository.save(status);
			} else {
				throw new DateIsNotAppropriate("Date property is not appropriate");
			}

		} else {
			throw new NoSuchPatientException("No Such Patient Exist");
		}
	}

	@Override
	public Status modifyPatientStatus(@Valid Status status) throws NoSuchStatusException, DateIsNotAppropriate {
		logger.info("For modifying PATIENT status");

		Status modifiedStatus = statusRepository.findStatusById(status.getStatusId());
		if (modifiedStatus != null) {
			if (((status.getConfirmDate().isBefore(status.getIsolationDate())
					|| status.getConfirmDate().isEqual(status.getIsolationDate())) && status.getConfirmDate() != null
					&& status.getIsolationDate() != null)
					&& !(status.getRecoveredDate() != null && status.getDeathDate() != null)) {

				if (!modifiedStatus.getConfirmDate().equals(status.getConfirmDate())) {
					modifiedStatus.setConfirmDate(status.getConfirmDate());
				}
				if (!modifiedStatus.getIsolationDate().equals(status.getIsolationDate())) {
					modifiedStatus.setIsolationDate(status.getIsolationDate());
				}
				modifiedStatus.setRecoveredDate(status.getRecoveredDate());
				modifiedStatus.setDeathDate(status.getDeathDate());

				return statusRepository.save(modifiedStatus);
			} else {
				throw new DateIsNotAppropriate("Date property is not appropriate");
			}

		} else {
			throw new NoSuchStatusException("No Status with given id");
		}
	}
}
