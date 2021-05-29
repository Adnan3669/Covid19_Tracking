package com.covid19.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalZone;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;
import com.covid19.service.UserService;

@SpringBootTest
class UserServiceImplUsingMockitoTest {
	@Autowired
	UserService userService;

	@MockBean
	StatusRepository statusRepository;
	@MockBean
	HospitalZoneRepositary hospitalZoneRepositary;
	@Autowired
	HospitalZone hospitalZone;
	@Autowired
	Hospital hospital;

	@Test
	void findTotalCases() {

		when(statusRepository.findTotalCases()).thenReturn(5);
		assertEquals(5, userService.findTotalCases());
	}

	@Test
	void findTotalLabTest() {
		when(statusRepository.findTotalLabTest()).thenReturn(5);
		assertEquals(5, userService.findTotalLabTest());

	}

	@Test
	void findTotalConfirmedCases() {
		when(statusRepository.findTotalConfirmedCases()).thenReturn(5);
		assertEquals(5, userService.findTotalConfirmedCases());

	}

	@Test
	void findTotalRecoveredCases() {
		when(statusRepository.findTotalRecoverdCases()).thenReturn(5);
		assertEquals(5, userService.findTotalRecoveredCases());
	}

	@Test
	void findTotalPatientInIsolation() {
		when(statusRepository.findTotalPatientInIsolation()).thenReturn(5);
		assertEquals(5, userService.findTotalPatientInIsolation());
	}

	@Test
	void findTotalDeath() {
		when(statusRepository.findTotalDeath()).thenReturn(5);
		assertEquals(5, userService.findTotalDeath());
	}

	@Test
	void findTotalCasesIn24Hrs() {
		when(statusRepository.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1))).thenReturn(5);
		assertEquals(5, userService.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1)));
	}

	@Test
	void findTotalDataBasedOnZoneAndDateTest() {
		hospital.setHospitalId(1);
		hospital.setHospitalName("Shridam Medicare");
		List<Hospital> hospitals = new ArrayList<>();
		hospitals.add(hospital);
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Mumbai");
		hospitalZone.setHospitals(hospitals);
		when(hospitalZoneRepositary.findHospitalZoneByName(hospitalZone.getZoneName())).thenReturn(hospitalZone);
		when(statusRepository.findTotalCasesinParticularDate(LocalDate.now(), hospital.getHospitalId())).thenReturn(1);
		when(statusRepository.findTotalDeathinParticularDate(LocalDate.now(), hospital.getHospitalId())).thenReturn(1);
		when(statusRepository.findTotalRecoveredinParticularDate(LocalDate.now(), hospital.getHospitalId()))
				.thenReturn(1);
		when(statusRepository.findTotalActiveinParticularDate(LocalDate.now(), hospital.getHospitalId())).thenReturn(1);
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("Confirmed Cases :" + 1);
		expectedList.add("Active Cases: " + 1);
		expectedList.add("Recovered Cases: " + 1);
		expectedList.add("Death cases: " + 1);

	}

}