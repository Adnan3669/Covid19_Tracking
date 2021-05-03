package com.covid19.Test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.covid19.exceptions.DateIsNotAppropriate;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.exceptions.NoSuchStatusException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.CovidTest;
import com.covid19.model.Hospital;
import com.covid19.model.Patient;
import com.covid19.model.Status;
import com.covid19.repository.PatientRepository;
import com.covid19.repository.PatientTestRepository;
import com.covid19.repository.StatusRepository;
import com.covid19.service.AdminService;
import com.covid19.service.PatientService;
import com.fasterxml.jackson.databind.Module.SetupContext;

@SpringBootTest
@Transactional
public class PatientServiceImplTest {

	@Autowired
	private PatientService patientService;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PatientTestRepository patientTestRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private Patient patient;
	@Autowired
	Hospital hospital;
	@Autowired
	AdminService adminService;
	@Autowired
	private CovidTest covidTest;
	@Autowired
	private Status status;

	@BeforeAll
	void setup() throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, NoSuchHospitalException {
		patient.setPatientFirstName("Hello");
		patient.setPatientLastName("World");
		patient.setPatientMobileNo(9876543210L);
		patient.setPatientAge(20);
		patient.setPatientGender("Male");
		patient = patientService.addPatient(98, patient);
	}

	@Test
	void testAddPatient() throws NoSuchAdminException, NoSuchHospitalException {

		Patient actual = patientRepository.findByPatientId(patient.getPatientId());
		assertEquals(patient, actual);
	}

	@Test
	void testAddPatientWhenThrowsConstraintViolationException() {
		assertThrows(ConstraintViolationException.class, () -> {
			patientService.addPatient(hospital.getHospitalId(), patient);
		});
	}

	@Test
	void testModifyPatient() throws NoSuchHospitalException, NoSuchPatientException, NoSuchAdminException {

		patient.setPatientFirstName("xyz");
		Patient expectedPatient = patientService.modifyPatient(patient);
		assertNotEquals(expectedPatient.getPatientFirstName(), "Hello");
	}

	@Test
	void testAddPatientTestDetails() throws NoSuchHospitalException, NoSuchPatientException, NoSuchAdminException {

		covidTest.setTestDate(LocalDate.of(2021, 04, 28));
		covidTest.setResult("Positive");
		CovidTest expectedTest = patientService.addPatientTestDetails(patient.getPatientId(), covidTest);
		CovidTest actualTest = patientTestRepository.getOne(expectedTest.getTestId());
		assertSame(expectedTest, actualTest);
	}

	@Test
	void testAddPatientStatus()
			throws NoSuchAdminException, NoSuchHospitalException, NoSuchPatientException, DateIsNotAppropriate {

		status.setConfirmDate(LocalDate.of(2021, 03, 10));
		status.setIsolationDate(LocalDate.of(2021, 03, 11));
		status.setRecoveredDate(LocalDate.of(2021, 03, 20));
		status.setDeathDate(null);
		Status expected = patientService.addPatientStatus(patient.getPatientId(), status);
		Status actual = statusRepository.getOne(expected.getStatusId());
		assertSame(expected, actual);
	}

	@Test
	void testModifyPatientStatus() throws NoSuchAdminException, NoSuchHospitalException, NoSuchPatientException,
			NoSuchStatusException, DateIsNotAppropriate {

		status.setConfirmDate(LocalDate.of(2021, 03, 10));
		status.setIsolationDate(LocalDate.of(2021, 03, 11));
		status.setRecoveredDate(LocalDate.of(2021, 03, 20));
		status.setDeathDate(null);
		Status expected = patientService.addPatientStatus(patient.getPatientId(), status);
		expected.setRecoveredDate(LocalDate.of(2021, 03, 21));
		expected = patientService.modifyPatientStatus(expected);
		assertEquals(expected.getRecoveredDate(), LocalDate.of(2021, 03, 21));
	}

}