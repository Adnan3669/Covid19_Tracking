package com.covid19.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalZone;
import com.covid19.entities.Patient;
import com.covid19.entities.Status;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;
import com.covid19.service.DeathPatientService;

@SpringBootTest
@Transactional
class DeathPatientServiceTestImplWithMockito {
	@Autowired
	DeathPatientService deathPatientService;

	@MockBean
	StatusRepository statusRepository;

	@Autowired
	Status status;

	@Autowired
	HospitalZone hospitalZone;

	@Autowired
	Hospital hospital;

	@MockBean
	HospitalZoneRepositary hospitalZoneRepositary;

	@Autowired
	Patient patient;

	@Test
	void testfindMonthWiseDeath() {
		when(statusRepository.findTotalDeathOfMonth(LocalDate.of(2021,5, 1),LocalDate.of(2021,5, 31))).thenReturn(1);
		int count=deathPatientService.findMonthWiseDeath(5);
		assertEquals(1, count);

	}

	@Test
	void testfindDivisionWiseDeath() {
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Virar");
		when(hospitalZoneRepositary.findHospitalZoneByName("Virar")).thenReturn(hospitalZone);
		when(statusRepository.findTotalDeathByZone(hospitalZone.getZoneId())).thenReturn(1);
		int count=deathPatientService.findDivisonWiseDeath("Virar");
		assertEquals(1, count);
	}

	@Test
	void testfindGenderWiseDeath() {
		when(statusRepository.findTotalDeathByAge(18,2)).thenReturn(1);
		int count = deathPatientService.findAgeWiseDeath(18,2);
		assertEquals(1,count);
	}

	@Test
	void testfindAgeWiseDeath() {
		when(statusRepository.findTotalDeathByGender("Male")).thenReturn(1);
		int count = deathPatientService.findGenderWiseDeath("Male");
		assertEquals(1,count);
	}
	
	@Test
	void testfindMonthWiseDeathThrowsConstrainViolationException() {
		assertThrows(ConstraintViolationException.class, ()->
		{
			deathPatientService.findMonthWiseDeath(13);
		});
		

	}

	@Test
	void testfindDivisionWiseDeathThrowsConstrainViolationException() {
	
		assertThrows(ConstraintViolationException.class, ()->
		{
			deathPatientService.findDivisonWiseDeath(null);
		});
	}

	@Test
	void testfindGenderWiseDeathThrowsConstrainViolationException() {
		assertThrows(ConstraintViolationException.class, ()->
		{
			 deathPatientService.findAgeWiseDeath(-1,-2);
		});
	}

	@Test
	void testfindAgeWiseDeathThrowsConstrainViolationException() {

		assertThrows(ConstraintViolationException.class, ()->
		{
			 deathPatientService.findGenderWiseDeath("Animal");
		});
	}

}