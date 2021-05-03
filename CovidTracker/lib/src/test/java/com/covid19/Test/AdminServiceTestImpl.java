package com.covid19.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.service.AdminService;
@SpringBootTest
 class AdminServiceTestImpl {

	@Autowired
	AdminService adminService;
	@Autowired
	Hospital hospital;
	@Transactional
	@Test
	 void TestAddHospitalAndGetHospital() throws NoSuchAdminException, NoSuchHospitalException, NoSuchTypeException, NoSuchZoneException
	{
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		Hospital expectedHospital = adminService.addHospital(7,hospital, 2, 97);
		Hospital actualHospital=adminService.getHospitalById(hospital.getHospitalId());
		assertEquals(expectedHospital, actualHospital);
	}
	@Transactional
	@Test
	 void GetHospitalForNoSuchHospitalException() throws NoSuchHospitalException
	{
		
		
		assertThrows(NoSuchHospitalException.class, ()->{
			if(adminService.getHospitalById(10000)==null)	
			adminService.getHospitalById(100000);
		});
		
	}
	@Transactional
	@Test
	 void TestRemoveHospitalById() throws NoSuchAdminException, NoSuchHospitalException, NoSuchTypeException, NoSuchZoneException
	{
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		hospital=adminService.addHospital(7,hospital, 2, 97);
		boolean flag= adminService.removeHospitalById(hospital.getHospitalId());
		assertTrue(flag);
	
	}
	@Transactional
	@Test
	 void TestRemoveHospitalByIdThrowsNoSuchHospitalException() throws NoSuchAdminException, NoSuchHospitalException
	{
	assertThrows(NoSuchHospitalException.class, ()->{
		adminService.removeHospitalById(10000);

	});

	}
}
