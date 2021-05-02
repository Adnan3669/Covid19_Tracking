package com.covid19.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;
import com.covid19.util.AdminCredentials;

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
	@MockBean
	AdminCredentials adminCredentials;
	@Autowired
	Admin admin;
	@MockBean
	AdminRepository adminRepository;

	@Test
	public void testfindHospitalByType() throws NoSuchAdminException, NoSuchTypeException {
		hospital.setHospitalName("ZYZ");
		hospital.setHospitalType(hospitalType);
		hospitalType.setTypeName("pr");

		List<Hospital> hospitalList = new ArrayList<>();
		hospitalList.add(hospital);
		when(hospitalTypeRepositary.findHospitalTypeByName(hospitalType.getTypeName())).thenReturn(hospitalType);
		when(hospitalRepository.findHospitalByType(hospital.getHospitalType().getTypeName())).thenReturn(hospitalList);
		List<Hospital> expectedHospital = hospitalService.findHospitalByType(hospital.getHospitalType().getTypeName());
		assertEquals(expectedHospital, hospitalList);

	}

	/*
	 * @Test public void findHospitalByTypeForNoSuchTypeException() throws
	 * NoSuchTypeException {
	 * when(hospitalRepository.findHospitalByType(hospital.getHospitalType())).
	 * thenReturn(nu); assertThrows(NoSuchTypeException.class, () -> {
	 * 
	 * hospitalService.findHospitalByType("Type not exist"); });
	 * 
	 * }
	 */

	@Test
	public void testfindHospitalByZone() throws NoSuchAdminException, NoSuchZoneException {
		hospital.setHospitalName("ZYZ");
		hospital.setHospitalZone(hospitalZone);
		hospitalZone.setZoneName("Borivali");

		List<Hospital> hospitalList = new ArrayList<>();
		hospitalList.add(hospital);
		when(hospitalZoneRepositary.findHospitalZoneByName(hospitalZone.getZoneName())).thenReturn(hospitalZone);
		when(hospitalRepository.findHospitalByZone(hospital.getHospitalZone().getZoneName())).thenReturn(hospitalList);
		List<Hospital> expectedHospital = hospitalService.findHospitalByZone(hospital.getHospitalZone().getZoneName());
		assertEquals(expectedHospital, hospitalList);
	}

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

	@Autowired
	List<Hospital> hospitalList;

	@Test
	public void testmodifyHospital() throws NoSuchAdminException, NoSuchHospitalException {
		admin.setAdminId(1);
		admin.setHospitals(hospitalList);
		admin.setAdminFirstName("sdg");
		admin.setAdminLastName("xgd");
		hospital.setHospitalId(1);
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);

		admin.getHospitals().add(hospital);
		when(adminRepository.save(admin)).thenReturn(admin);

		Hospital actualHospital = adminService.addHospital(admin.getAdminId(), hospital);
		hospital.setHospitalName("abc");
		when(hospitalRepository.findByHospitalId(hospital.getHospitalId())).thenReturn(hospital);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);

		Hospital expectedHospital = hospitalService.modifyHospital(hospital);
		assertNotEquals("Jairam Hospital", expectedHospital.getHospitalName());

	}
}