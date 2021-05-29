package com.covid19.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.covid19.entities.CovidTest;
import com.covid19.entities.Hospital;
import com.covid19.entities.Patient;
import com.covid19.entities.Status;
import com.covid19.exceptions.DateIsNotAppropriate;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.exceptions.NoSuchStatusException;
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
	/*  Adds Patient by Accepting hospitalId and Patient details.  */
	public Patient addPatient(@Positive int hospitalId, @Valid Patient patient) throws NoSuchHospitalException {
		logger.info("For adding PATIENT");
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);            /* find hospital by calling this method. */
		if (hospital != null) {										/* check hospital is null or not, if not null then patient will be add. */
			patient.setHospital(hospital);							
			patientRepository.save(patient);                        // inserting record in patient table.
			hospital.getPatients().add(patient);                    
			hospitalRepository.save(hospital);                      //add inserted patient in hospital using hospital foreign key.
			return patient;
		} else {
			throw new NoSuchHospitalException("No Such Hospital Exists");   //if hospital is not present with given id then this exception will be thrown.
		}
	}

	@Override
	/* modifies patient by accepting hospital id and patient details. */
	public Patient modifyPatient(@Valid Patient patient) throws NoSuchPatientException {
		logger.info("For modifying PATIENT");

		Patient modifypatient = patientRepository.findByPatientId(patient.getPatientId());   //will check patient in database 
		if (modifypatient != null) {                                                         //if patient is not null then will modify.

			modifypatient.setPatientFirstName(patient.getPatientFirstName());

			modifypatient.setPatientLastName(patient.getPatientLastName());

			modifypatient.setPatientAge(patient.getPatientAge());

			modifypatient.setPatientMobileNo(patient.getPatientMobileNo());

			modifypatient.setPatientGender(patient.getPatientGender());

			return patientRepository.save(modifypatient);
		} else {
			throw new NoSuchPatientException("No Such Patient Exist ");                      //if patient is null then will throw this exception.
		}
	}

	@Override
	/* adds patient Test details by accepting patient id and covidTest details. */
	public Status addPatientTestDetails(@Positive int patientId, @Valid CovidTest covidTest)
			throws NoSuchPatientException, DateIsNotAppropriate, NoSuchStatusException {
		logger.info("For adding PATIENT test details");
		Patient patient = patientRepository.findByPatientId(patientId);              //find patient by patient Id.
		if (patient != null) {                                              // if patient is not null then will add test details.
			covidTest.setPatient(patient);
			patientTestRepository.save(covidTest);
			if(covidTest.getResult().equals("Positive") )
			{
			Status status =new Status();
			status.setConfirmDate(LocalDate.now());
			status.setIsolationDate(LocalDate.now());
		return	addPatientStatus(patientId, status);
			}
			else {
				Status status=statusRepository.findStatusByPatientId(patientId);
				if(status!=null)
				{
					status.setRecoveredDate(LocalDate.now());
					return modifyPatientStatus(status);
				}
				else {
					return new Status();
				}
			}
			
		} else {
			throw new NoSuchPatientException("No Such Patient Exist");          //if patient is null then exception will be thrown.
		}
	}

	@Override
	/* add patient Status by accepting patient id and patient status details. */
	public Status addPatientStatus(@Positive int patientId, @Valid Status status)
			throws NoSuchPatientException, DateIsNotAppropriate {
		logger.info("For adding PATIENT status");

		Patient patient = patientRepository.findByPatientId(patientId);              // find patient by id.
		if (patient != null) {                                          //check if patient is null.
			/*
			 * checks all the condition for status of patient .
			 * 1)confirm date should be before or equals to isolation date.
			 * 2)confirm date and isolation date can not be null.
			 * 3)one patient can not have recovered date and death date at same time.
			 * if conditions not followed throws Exception. 
			 */
			if (((status.getConfirmDate().isBefore(status.getIsolationDate())                                         
					|| status.getConfirmDate().isEqual(status.getIsolationDate())) && status.getConfirmDate() != null
					&& status.getIsolationDate() != null)
					&& !(status.getRecoveredDate() != null && status.getDeathDate() != null)) {

				status.setConfirmDate(status.getConfirmDate());
				status.setIsolationDate(status.getIsolationDate());
				status.setRecoveredDate(status.getRecoveredDate());
				status.setDeathDate(status.getDeathDate());
				status.setPatient(patient);
				return statusRepository.save(status);
			} else {
				throw new DateIsNotAppropriate("Date property is not appropriate");              //throws exception if date is not appropriate.
			}

		} else {
			throw new NoSuchPatientException("No Such Patient Exist");                         // throws exception if patient does not exist.
		}
	}

	@Override
	/*
	 * checks all the condition for status of patient .
	 * 1)confirm date should be before or equals to isolation date.
	 * 2)confirm date and isolation date can not be null.
	 * 3)one patient can not have recoverd date and death date at same time.
	 * if conditions not followed then throws exception.
	 */
	public Status modifyPatientStatus(@Valid Status status) throws NoSuchStatusException, DateIsNotAppropriate {
		logger.info("For modifying PATIENT status");

		Status modifiedStatus = statusRepository.findStatusById(status.getStatusId());
		if (modifiedStatus != null) {
			if(status.getDeathDate()==null)
			{
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
				throw new DateIsNotAppropriate("Date property is not appropriate1");               //Throws date not appropriate exception.
			}
			}
			else {
				throw new DateIsNotAppropriate("Date property is not appropriate2");               //Throws date not appropriate exception.

			}

		} else {
			throw new NoSuchStatusException("No Status with given id");                     
		}
	}
	@Override
	public List<Patient> findAllPatients() {
		logger.info("For finding all Patients details");
		return patientRepository.findAll();
	}
	
	
}