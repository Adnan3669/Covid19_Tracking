package com.covid19.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.entity.Hospital;
import com.covid19.entity.HospitalZone;
import com.covid19.entity.Patient;
import com.covid19.entity.Status;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;
import com.covid19.service.DeathPatientService;

@SpringBootTest
@Transactional
class DeathPatientServiceTestImpl {
	@Autowired
	DeathPatientService deathPatientService;

	@Autowired
	StatusRepository statusRepository;

	@Autowired
	Status status;

	@Autowired
	HospitalZone hospitalZone;

	@Autowired
	Hospital hospital;

	@Autowired
	HospitalZoneRepositary hospitalZoneRepositary;

	@Autowired
	Patient patient;

	@Test
	void testfindMonthWiseDeath() {
		status.setConfirmDate(LocalDate.of(2021, 03, 21));
		status.setIsolationDate(LocalDate.of(2021, 03, 21));
		status.setDeathDate(LocalDate.of(2021, 05, 01));
		statusRepository.save(status);
		int count = deathPatientService.findMonthWiseDeath(05);
		assertEquals(1, count);

	}

	@Test
	void testfindDivisionWiseDeath() {
		int count = deathPatientService.findDivisonWiseDeath("Virar");
		assertEquals(1,count);
	}

	@Test
	void testfindGenderWiseDeath() {
		int count = deathPatientService.findGenderWiseDeath("Male");
		assertEquals(1,count);
	}

	@Test
	void testfindAgeWiseDeath() {
		int count = deathPatientService.findAgeWiseDeath(18);
		assertEquals(1,count);
	}

}