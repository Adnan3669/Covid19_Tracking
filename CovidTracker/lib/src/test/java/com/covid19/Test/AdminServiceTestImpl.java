package com.covid19.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.covid19.service.AdminService;

@Transactional
@SpringBootTest
class AdminServiceImplTest {

	@Autowired
	AdminService adminService;
	@Autowired
	Hospital hospital;
	@Autowired
	Admin admin;
	
	@BeforeEach
	void setup()
	{
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		
	}
	
	@Test
	 void addAdmin() throws NoSuchAdminException, AdminException {
		
		adminService.addAdmin(admin);
		adminService.getAdminById(admin.getAdminId());
	}
	@Test
	void addHospital() throws NoSuchAdminException, NoSuchHospitalException, NoSuchTypeException, NoSuchZoneException {
		

	}

	@Test
	void GetHospitalForNoSuchHospitalException() throws NoSuchHospitalException {

		assertThrows(NoSuchHospitalException.class, () -> {
			adminService.getHospitalById(100000);
		});

	}

	@Test
	void TestRemoveHospitalById()
			throws NoSuchAdminException, NoSuchHospitalException, NoSuchTypeException, NoSuchZoneException {
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		hospital = adminService.addHospital(7, hospital, 2, 97);
		boolean flag = adminService.removeHospitalById(hospital.getHospitalId());
		assertTrue(flag);

	}

	@Test
	void TestRemoveHospitalByIdThrowsNoSuchHospitalException() throws NoSuchAdminException, NoSuchHospitalException {
		assertThrows(NoSuchHospitalException.class, () -> {
			adminService.removeHospitalById(10000);

		});

	}
}
