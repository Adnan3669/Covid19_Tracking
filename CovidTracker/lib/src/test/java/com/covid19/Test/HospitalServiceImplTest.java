package com.covid19.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;

@SpringBootTest
@Transactional
class HospitalServiceImplTest {
	@Autowired
	HospitalService hospitalService;

	@Autowired
	HospitalType hospitalType;

	@Autowired
	Hospital hospital;

	@Autowired
	AdminService adminService;

	@Autowired
	HospitalZone hospitalZone;

	@Test
	void testfindHospitalByType() throws NoSuchAdminException, NoSuchTypeException {
		hospital.setHospitalName("ZYZ");
		hospitalType.setTypeName("Goverment1");
		hospitalType = hospitalService.addHospitalType(hospitalType);
		hospital.setHospitalType(hospitalType);
		Hospital expectedHospital = adminService.addHospital(1, hospital);
		boolean value = hospitalService.findHospitalByType("Goverment1").contains(expectedHospital);
		assertTrue(value);
	}

	@Test
	void testfindHospitalByZone() throws NoSuchAdminException, NoSuchZoneException {
		hospital.setHospitalName("ZYZ");
		hospitalZone.setZoneName("Borivali");
		hospitalZone = hospitalService.addHospitalZone(hospitalZone);
		hospital.setHospitalZone(hospitalZone);
		Hospital expectedHospital = adminService.addHospital(1, hospital);
		boolean value = hospitalService.findHospitalByZone("Borivali").contains(expectedHospital);
		assertTrue(value);
	}

	@Test
	void testfindAllHospital() throws NoSuchAdminException {
		hospital.setHospitalName("ZYZ");
		Hospital expectedHospital = adminService.addHospital(1, hospital);
		boolean value = hospitalService.findAllHospitals().contains(expectedHospital);
		assertTrue(value);
	}

	@Test
	void testfindHospitalByFreeBeds() throws NoSuchAdminException {
		hospital.setHospitalName("ZYZ");
		hospital.setHospitalGeneralBed(0);
		hospital.setHospitalICUBed(0);
		Hospital expectedHospital = adminService.addHospital(1, hospital);
		boolean value = hospitalService.findHospitalByFreeBeds().contains(expectedHospital);
		assertFalse(value);
	}

	@Test
	void testmodifyHospital() throws NoSuchAdminException, NoSuchHospitalException {
		hospital.setHospitalName("ZYZ");
		Hospital expectedHospital = adminService.addHospital(1, hospital);
		expectedHospital.setHospitalName("abc");
		expectedHospital = hospitalService.modifyHospital(expectedHospital);
		assertNotEquals("ZYZ", expectedHospital.getHospitalName());
	}

}