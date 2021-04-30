package com.covid19.Test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.model.Hospital;
import com.covid19.model.Patient;
import com.covid19.model.Result;
import com.covid19.model.CovidTest;
import com.covid19.repository.PatientRepository;
import com.covid19.repository.ResultRepository;
import com.covid19.repository.PatientTestRepository;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;
import com.covid19.service.PatientService;

@SpringBootTest
@Transactional
public class PatientServiceImplTest {

	@Autowired
	private PatientService patientService;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private ResultRepository resultRepository;
	@Autowired
	private PatientTestRepository patientTestRepository;
	@Autowired
	private Patient patient;
	@Autowired
	private Result result;
	@Autowired
	Hospital hospital;
	@Autowired
	AdminService adminService;
	@Autowired
	private CovidTest covidTest;

	@Test
	void testAddPatient() throws NoSuchAdminException {

		patient.setPatientFirstName("Hello");
		patient.setPatientLastName("World");
		patient.setPatientMobileNo(9876543210L);
		patient.setPatientAge(20);
		patient.setPatientGender("M");
		hospital.setHospitalName("Mahadev Hospital");
		hospital = adminService.addHospital(hospital);
		Patient expected = patientService.addPatient(hospital.getHospitalId(), patient);
		Patient actual = patientRepository.findByPatientId(expected.getPatientId());
		assertEquals(expected, actual);
	}

	@Test
	void testModifyPatient() {
		patient.setPatientFirstName("Hello");
		patient.setPatientLastName("World");
		patient.setPatientMobileNo(9876543210L);
		patient.setPatientAge(20);
		patient.setPatientGender("M");
		hospital.setHospitalName("Mahadev Hospital");
		Patient expectedPatient = patientService.addPatient(hospital.getHospitalId(), patient);
		expectedPatient.setPatientFirstName("xyz");
		expectedPatient = patientService.modifyPatient(expectedPatient);
		assertNotEquals(expectedPatient.getPatientFirstName(), "Hello");
	}

	@Test
	void testAddPatientTestDetails() {
		patient.setPatientFirstName("Hello");
		patient.setPatientLastName("World");
		patient.setPatientMobileNo(9876543210L);
		patient.setPatientAge(20);
		patient.setPatientGender("M");
		hospital.setHospitalName("Mahadev Hospital");
		Patient expectedPatient = patientService.addPatient(hospital.getHospitalId(), patient);
		covidTest.setTestDate(LocalDate.of(2021, 04, 28));
		CovidTest expectedTest = patientService.addPatientTestDetails(expectedPatient.getPatientId(), covidTest);
		CovidTest actualTest = patientTestRepository.getOne(expectedTest.getTestId());
		assertSame(expectedTest, actualTest);
	}

	@Test
	void testAddResult() {
		result.setResultVal("Positive");
		Result expectedResult = patientService.addResult(result);
		Result actualResult = resultRepository.getOne(expectedResult.getResultId());
		assertSame(expectedResult, actualResult);
	}
}