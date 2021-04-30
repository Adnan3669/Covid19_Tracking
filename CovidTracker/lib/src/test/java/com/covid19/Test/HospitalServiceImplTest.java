package com.covid19.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;

@SpringBootTest
@Transactional
public class HospitalServiceImplTest {
	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private HospitalType hospitalType;

	@Autowired
	private Hospital hospital;

	@Autowired
	private AdminService adminService;

	@Autowired
	private HospitalZone hospitalZone;

	@Test
	public void testfindHospitalByType() throws NoSuchAdminException, NoSuchTypeException {
		hospital.setHospitalName("ZYZ");
		hospitalType.setTypeName("Goverment1");
		hospitalType = hospitalService.addHospitalType(hospitalType);
		hospital.setHospitalType(hospitalType);
		Hospital expectedHospital = adminService.addHospital(hospital);
		boolean value = hospitalService.findHospitalByType("Goverment1").contains(expectedHospital);
		assertTrue(value);
	}

	@Test
	public void testfindHospitalByZone() throws NoSuchAdminException, NoSuchZoneException {
		hospital.setHospitalName("ZYZ");
		hospitalZone.setZoneName("Borivali");
		hospitalZone = hospitalService.addHospitalZone(hospitalZone);
		hospital.setHospitalZone(hospitalZone);
		Hospital expectedHospital = adminService.addHospital(hospital);
		boolean value = hospitalService.findHospitalByZone("Borivali").contains(expectedHospital);
		assertTrue(value);
	}

	@Test
	public void testfindAllHospital() throws NoSuchAdminException {
		hospital.setHospitalName("ZYZ");
		Hospital expectedHospital = adminService.addHospital(hospital);
		boolean value = hospitalService.findAllHospitals().contains(expectedHospital);
		assertTrue(value);
	}

	@Test
	public void testfindHospitalByFreeBeds() throws NoSuchAdminException {
		hospital.setHospitalName("ZYZ");
		hospital.setHospitalGeneralBed(0);
		hospital.setHospitalICUBed(0);
		Hospital expectedHospital = adminService.addHospital(hospital);
		boolean value = hospitalService.findHospitalByFreeBeds().contains(expectedHospital);
		assertFalse(value);
	}

	@Test
	public void testmodifyHospital() throws NoSuchAdminException {
		hospital.setHospitalName("ZYZ");
		Hospital expectedHospital = adminService.addHospital(hospital);
		expectedHospital.setHospitalName("abc");
		expectedHospital = hospitalService.modifyHospital(expectedHospital);
		assertNotEquals(expectedHospital.getHospitalName(), "ZYZ");
	}

}