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
import com.covid19.model.Hospital;
import com.covid19.service.AdminService;
@SpringBootTest
public class AdminServiceTestImpl {

	@Autowired
	AdminService adminService;
	@Autowired
	Hospital hospital;
	@Transactional
	@Test
	public void TestAddHospitalAndGetHospital() throws NoSuchAdminException, NoSuchHospitalException
	{
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		Hospital expectedHospital=adminService.addHospital(1,hospital);
		Hospital actualHospital=adminService.getHospitalById(hospital.getHospitalId());
		assertEquals(expectedHospital, actualHospital);
	}
	@Transactional
	@Test
	public void GetHospitalForNoSuchHospitalException() throws NoSuchHospitalException
	{
		assertThrows(NoSuchHospitalException.class, ()->{
			
			adminService.getHospitalById(10000);
		});
		
	}
	@Transactional
	@Test
	public void TestRemoveHospitalById() throws NoSuchAdminException, NoSuchHospitalException
	{
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		hospital=adminService.addHospital(1,hospital);
		boolean flag= adminService.removeHospitalById(hospital.getHospitalId());
		assertTrue(flag);
	
	}
	@Transactional
	@Test
	public void TestRemoveHospitalByIdThrowsNoSuchHospitalException() throws NoSuchAdminException, NoSuchHospitalException
	{
	assertThrows(NoSuchHospitalException.class, ()->{
		adminService.removeHospitalById(10000);

	});

	}
}
