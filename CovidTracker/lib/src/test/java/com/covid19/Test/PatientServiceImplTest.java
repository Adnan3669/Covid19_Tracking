package com.covid19.Test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.validation.ConstraintDeclarationException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchPatientException;
import com.covid19.model.CovidTest;
import com.covid19.model.Hospital;
import com.covid19.model.Patient;
import com.covid19.repository.PatientRepository;
import com.covid19.repository.PatientTestRepository;
import com.covid19.service.AdminService;
import com.covid19.service.PatientService;

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
	private Patient patient;
	@Autowired
	Hospital hospital;
	@Autowired
	AdminService adminService;
	@Autowired
	private CovidTest covidTest;
	
	@Test
	void testAddPatient() throws NoSuchAdminException, NoSuchHospitalException {

		patient.setPatientFirstName("Hello");
		patient.setPatientLastName("World");
		patient.setPatientMobileNo(9876543210L);
		patient.setPatientAge(20);
		patient.setPatientGender("M");
		hospital.setHospitalName("Mahadev Hospital");
		hospital = adminService.addHospital(1,hospital);
		Patient expected = patientService.addPatient(hospital.getHospitalId(), patient);
		Patient actual = patientRepository.findByPatientId(expected.getPatientId());
		assertEquals(expected, actual);
	}
    @Test
    void testAddPatientWhenThrowsConstraintViolationException()  {
        assertThrows(ConstraintDeclarationException.class, ()-> {
            //patient.setPatientFirstName("Hello");
            patient.setPatientLastName("World");
            patient.setPatientMobileNo(9876543210L);
            patient.setPatientAge(20);
            patient.setPatientGender("M");
            hospital.setHospitalName("Mahadev Hospital");
            hospital = adminService.addHospital(1,hospital);
            patientService.addPatient(hospital.getHospitalId(), patient);
        }, "Patient Field is not appropriate");
    }
	@Test
	void testModifyPatient() throws NoSuchHospitalException, NoSuchPatientException  {
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
	void testAddPatientTestDetails() throws NoSuchHospitalException, NoSuchPatientException  {
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


}