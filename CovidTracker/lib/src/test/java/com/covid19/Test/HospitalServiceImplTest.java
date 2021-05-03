package com.covid19.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Admin;
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
	Hospital hospital;

	@Autowired
	HospitalType hospitalType;
	@Autowired
	HospitalZone hospitalZone;
	@Autowired
	AdminService adminService;

	

	@Autowired
	Admin admin;
	
	@BeforeEach
	void setup() throws AdminException {
		
	

	}

	@Test
	void testfindHospitalByType() throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		admin.setAdminId(1);
		admin=adminService.addAdmin(admin);
		hospitalType.setTypeName("Government");
		hospitalType=hospitalService.addHospitalType(hospitalType);
		hospitalZone.setZoneName("Borivali");
		hospitalZone=hospitalService.addHospitalZone(hospitalZone);
		hospital.setHospitalName("ZYZ");
		 hospital=adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		boolean value = hospitalService.findHospitalByType("Goverment").contains(hospital);
		assertTrue(value);
	}

	@Test
	void testfindHospitalByZone() throws NoSuchAdminException, NoSuchZoneException, NoSuchTypeException {
		 adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		boolean value = hospitalService.findHospitalByZone("Borivali").contains(hospital);
		assertTrue(value);
	}

	@Test
	void testfindAllHospital() throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		 adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		boolean value = hospitalService.findAllHospitals().contains(hospital);
		assertTrue(value);
	}

	@Test
	void testfindHospitalByFreeBeds() throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		 adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		boolean value = hospitalService.findHospitalByFreeBeds().contains(hospital);
		assertFalse(value);
	}



}