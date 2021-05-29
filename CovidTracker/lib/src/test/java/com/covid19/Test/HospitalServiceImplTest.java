package com.covid19.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.covid19.entity.Admin;
import com.covid19.entity.Hospital;
import com.covid19.entity.HospitalType;
import com.covid19.entity.HospitalZone;
import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;
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
	HospitalTypeRepositary typeRepositary;
	@Autowired
	HospitalZoneRepositary zoneRepositary;

	@Autowired
	Admin admin;
	
	//Declaring BeforeEach annotation
	@BeforeEach
	void setup() throws AdminException, NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		hospital.setHospitalName("Namita hospital");
		hospital=adminService.addHospital(2, hospital, 1, 1);
	}

	//Testing findHospitalByType 
	@Test
	void testfindHospitalByType() throws NoSuchAdminException, NoSuchTypeException{
		
		boolean value = hospitalService.findHospitalByType("Government").contains(hospital);
		assertTrue(value);
	}
	
	//Testing findHospitalByType for NoSuchAdminException
	@Test
	void findHospitalByTypeForNoSuchAdminException()throws NoSuchAdminException{
		assertThrows(NoSuchAdminException.class, ()->{
			hospital=adminService.addHospital(3, hospital, 1, 1);  //For No SuchAdminException
			boolean value = hospitalService.findHospitalByType("Goverment").contains(hospital);
			assertTrue(value);
		});
	}
	
	//Testing findHospitalByType For NoSuchTypeException
	@Test
	void findHospitalByTypeForNoSuchTypeException()throws NoSuchTypeException{
		assertThrows(NoSuchTypeException.class, ()->{
			boolean value = hospitalService.findHospitalByType("Goverment").contains(hospital);  // For NoSuchTypeException
			assertTrue(value);
		});
	}
	

	//Testing findHospitalByZone 
	@Test
	void testfindHospitalByZone() throws NoSuchAdminException, NoSuchZoneException, NoSuchTypeException {
		boolean value = hospitalService.findHospitalByZone("Gujarat").contains(hospital);
		assertTrue(value);
	}
	
	//Testing findHospitalByZone For NoSuchAdminException
	@Test
	void findHospitalByZoneForNoSuchAdminException()throws NoSuchAdminException{
		assertThrows(NoSuchAdminException.class, ()->{
			hospital=adminService.addHospital(3, hospital, 1, 1);  //For No Such Admin Exception
			boolean value = hospitalService.findHospitalByZone("Gujarat").contains(hospital);
			assertTrue(value);
		});
	}
	
	//Testing findHospitalByZone For NoSuchZoneException
	@Test
	void findHospitalByZoneForNoSuchZoneException()throws NoSuchZoneException{
		assertThrows(NoSuchZoneException.class, ()->{
			boolean value = hospitalService.findHospitalByZone("Gujrat").contains(hospital);
			assertTrue(value);
		});
	}
	

	//Testing FindAllHospitals
	@Test
	void testfindAllHospital() throws NoSuchAdminException {
		boolean value = hospitalService.findAllHospitals().contains(hospital);
		assertTrue(value);
	}
	
	//Testing FindAllHospitals For NoSuchAdminException
	@Test
	void findAllHospitalForNoSuchAdminException()throws NoSuchAdminException{
		assertThrows(NoSuchAdminException.class, ()->{
			hospital=adminService.addHospital(3, hospital, 1, 1);  //For No Such Admin Exception
			boolean value = hospitalService.findAllHospitals().contains(hospital);
			assertTrue(value);
		});
	}
	
	//Testing HospitleByFreeBeds
	@Test
	void testfindHospitalByFreeBeds() throws NoSuchAdminException{
		boolean value = hospitalService.findHospitalByFreeBeds().contains(hospital);
		assertFalse(value);
	}
	
	//Testing HospitleByFreeBeds For NoSuchAdminException
	@Test
	void findHospitalByFreeBedsForNoSuchAdminException()throws NoSuchAdminException{
		assertThrows(NoSuchAdminException.class, ()->{
			hospital=adminService.addHospital(3, hospital, 1, 1);  //For No Such Admin Exception
			boolean value = hospitalService.findHospitalByFreeBeds().contains(hospital);
			assertFalse(value);
		});
	}
	
	//Testing AddHospitleType
	@Test
	void testAddHospitalType()throws NoSuchAdminException,NoSuchHospitalException{
		hospitalType.setTypeName("Private");
		HospitalType first= hospitalService.addHospitalType(hospitalType);
		HospitalType second= typeRepositary.findHospitalTypeByName("Private");
		assertEquals(first, second);
		
	}
	
	//Testing AllHospitleType
	@Test
	void testgetAllHospitalType(){
		hospitalType.setTypeName("Goverment");
		HospitalType first= hospitalService.addHospitalType(hospitalType);
		boolean value=hospitalService.getAllHospitalType().contains(first);
		assertTrue(value);
	}
	
	//Testing AllHospitalZone
	@Test
	void testgetAllHospitalZone(){
		hospitalZone.setZoneName("Dahisar");
		HospitalZone first= hospitalService.addHospitalZone(hospitalZone);
		boolean value=hospitalService.getAllHospitalZones().contains(first);
		assertTrue(value);
	}
	
	//Testing AddHospitalZone
	@Test
	void testAddHospitalZone(){
		hospitalZone.setZoneName("Dahisar");
		HospitalZone first= hospitalService.addHospitalZone(hospitalZone);
		HospitalZone second= zoneRepositary.findHospitalZoneByName("Dahisar");
		assertEquals(first, second);
	}
}