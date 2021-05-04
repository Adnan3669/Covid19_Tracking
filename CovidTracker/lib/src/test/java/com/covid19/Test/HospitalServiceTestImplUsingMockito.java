package com.covid19.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.entities.Admin;
import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalType;
import com.covid19.entities.HospitalZone;
import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;

@SpringBootTest
public class HospitalServiceTestImplUsingMockito {
	@Autowired
	HospitalService hospitalService;
	@MockBean
	HospitalRepository hospitalRepository;
	@MockBean
	HospitalTypeRepositary hospitalTypeRepositary;
	@MockBean
	HospitalZoneRepositary hospitalZoneRepositary;
	@Autowired
	Hospital hospital;
	@Autowired
	HospitalType hospitalType;
	@Autowired
	HospitalZone hospitalZone;
	@Autowired
	private AdminService adminService;

	@Autowired
	Admin admin;
	@MockBean
	AdminRepository adminRepository;

	// Testing FindHospitalByType
	@Test
	void testfindHospitalByType() throws NoSuchAdminException, NoSuchTypeException, AdminException {
		admin.setAdminFirstName("Gaurav");
		admin.setAdminLastName("Singh");
		admin.setAdminUserName("gaurav");
		admin.setAdminPassword("GGG@123");
		admin.setAdminId(2);
		adminService.addAdmin(admin);
		hospital.setHospitalName("Namita Hospital");
		hospital.setHospitalType(hospitalType);
		hospitalType.setTypeName("private");
		List<Hospital> hospitalList = new ArrayList<>();
		hospitalList.add(hospital);
		when(hospitalTypeRepositary.findHospitalTypeByName(hospitalType.getTypeName())).thenReturn(hospitalType);
		when(hospitalRepository.findHospitalByType(hospital.getHospitalType().getTypeName())).thenReturn(hospitalList);
		List<Hospital> expectedHospital = hospitalService.findHospitalByType("private");
		assertEquals(expectedHospital, hospitalList);

	}

	// Testing FindHospitalByType For NoSuchTypeException
	@Test
	void findHospitalByTypeForNoSuchTypeException() throws NoSuchTypeException {
		assertThrows(NoSuchTypeException.class, () -> {
			hospital.setHospitalName("Namita Hospital");
			hospital.setHospitalType(hospitalType);
			hospitalType.setTypeName("private");

			List<Hospital> hospitalList = new ArrayList<>();
			hospitalList.add(hospital);
			when(hospitalTypeRepositary.findHospitalTypeByName(hospitalType.getTypeName())).thenReturn(hospitalType);
			when(hospitalRepository.findHospitalByType(hospital.getHospitalType().getTypeName()))
					.thenReturn(hospitalList);
			List<Hospital> expectedHospital = hospitalService.findHospitalByType("privatea");
			assertEquals(expectedHospital, hospitalList);
		});
	}

	// Testing FindHospitalByZone
	@Test
	public void testfindHospitalByZone() throws NoSuchAdminException, NoSuchZoneException {
		hospital.setHospitalName("ZYZ");
		hospital.setHospitalZone(hospitalZone);
		hospitalZone.setZoneName("Borivali");

		List<Hospital> hospitalList = new ArrayList<>();
		hospitalList.add(hospital);
		when(hospitalZoneRepositary.findHospitalZoneByName(hospitalZone.getZoneName())).thenReturn(hospitalZone);
		when(hospitalRepository.findHospitalByZone(hospital.getHospitalZone().getZoneName())).thenReturn(hospitalList);
		List<Hospital> expectedHospital = hospitalService.findHospitalByZone("Borivali");
		assertEquals(expectedHospital, hospitalList);
	}

	// Testing FindHospitalByZone For NoSuchZoneException
	@Test
	void findHospitalByZoneForNoSuchZoneException() throws NoSuchZoneException {
		assertThrows(NoSuchZoneException.class, () -> {
			hospital.setHospitalName("City Hospital");
			hospital.setHospitalZone(hospitalZone);
			hospitalZone.setZoneName("Borivali");

			List<Hospital> hospitalList = new ArrayList<>();
			hospitalList.add(hospital);
			when(hospitalZoneRepositary.findHospitalZoneByName(hospitalZone.getZoneName())).thenReturn(hospitalZone);
			when(hospitalRepository.findHospitalByZone(hospital.getHospitalZone().getZoneName()))
					.thenReturn(hospitalList);
			List<Hospital> expectedHospital = hospitalService.findHospitalByZone("Borivalia");
			assertEquals(expectedHospital, hospitalList);
		});
	}

	// Testing FindHospitalByFreeBeds
	@Test
	public void testfindHospitalByFreeBeds() throws NoSuchAdminException {
		hospital.setHospitalName("ZYZ");
		hospital.setHospitalGeneralBed(0);
		hospital.setHospitalICUBed(0);
		List<Hospital> hospitalList = new ArrayList<>();
		hospitalList.add(hospital);
		when(hospitalRepository.findHospitalByFreeBeds()).thenReturn(hospitalList);
		List<Hospital> expectedHospital = hospitalService.findHospitalByFreeBeds();
		assertEquals(expectedHospital, hospitalList);
	}

}