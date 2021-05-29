package com.covid19.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.entity.Admin;
import com.covid19.entity.Hospital;
import com.covid19.entity.HospitalType;
import com.covid19.entity.HospitalZone;
import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;

@SpringBootTest
class AdminServiceImplUsingMockitoTest {

	@MockBean
	HospitalZoneRepositary zoneRepository;
	@MockBean
	HospitalTypeRepositary typeRepository;

	@Autowired
	AdminService adminService;
	@MockBean
	HospitalRepository hospitalRepository;
	@MockBean
	AdminRepository adminRepository;
	@MockBean
	HospitalService hospitalService;

	@Autowired
	Hospital hospital;
	@Autowired
	Admin admin;
	@Autowired
	HospitalType hospitalType;
	@Autowired
	HospitalZone hospitalZone;

	@BeforeEach
	void setup() {
		admin.setAdminId(1);
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		List<Hospital> hpList = new ArrayList<>();
		admin.setHospitals(hpList);

	}

	/* Test Case:1-> for AddAdmin function */
	@Test
	void addAdminTest() throws AdminException {
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(null);
		when(adminRepository.save(admin)).thenReturn(admin);

		Admin expectedAdmin = adminService.addAdmin(admin);
		assertEquals(admin, expectedAdmin);
	}

	/* Test Case:2 -> for AddAdmin with AdminExceptionTest function */
	@Test
	void addAdminWithAdminExceptionTest() throws AdminException, NoSuchAdminException {
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);
		assertThrows(AdminException.class, () -> {

			adminService.addAdmin(admin);

		});

	}

	/* Test Case 3:-> for getAdminByAdminTest */
	@Test
	void getAdminByAdminIdTest() throws AdminException, NoSuchAdminException {
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);
		Admin actualAdmin = adminService.getAdminById(admin.getAdminId());
		assertEquals(admin, actualAdmin);
	}

	/* Test Case 4:-> for getAdminByAdminTest with NoSuchAdminException function */
	@Test
	void getAdminByAdminIdWithNoSuchAdminExceptionTest() throws AdminException, NoSuchAdminException {
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(null);
		assertThrows(NoSuchAdminException.class, () -> {
			adminService.getAdminById(admin.getAdminId());

		});
	}

	/* Test Case 5:-> for getAllAdmins */
	@Test
	void getAllAdminsTest() throws AdminException {
		when(adminRepository.findAll()).thenReturn(null);
		assertEquals(null,adminService.getAllAdmins());
	}

	/* Test Case 6:-> for addHospital */

	@Test
	void addHospitalTest() throws AdminException, NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		admin.setAdminId(1);
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		hospitalType.setTypeId(1);
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Gujarat");
		hospital.setHospitalName("jairam hospital");
		hospital.setHospitalId(1);
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);
		when(typeRepository.findHospitalTypeById(hospitalType.getTypeId())).thenReturn(hospitalType);
		when(zoneRepository.findHospitalZoneById(hospitalZone.getZoneId())).thenReturn(hospitalZone);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);

		Hospital expectedHospital = adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(),
				hospitalType.getTypeId());
		assertEquals(hospital, expectedHospital);
	}

	/* Test Case 7:-> for addHospital with NoSuchAdminException */
	@Test
	void addHospitalTestWithNoSuchAdminException()
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin.setAdminId(1);
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		hospitalType.setTypeId(1);
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Gujarat");
		hospital.setHospitalName("jairam hospital");
		hospital.setHospitalId(1);
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(null);
		when(typeRepository.findHospitalTypeById(hospitalType.getTypeId())).thenReturn(hospitalType);
		when(zoneRepository.findHospitalZoneById(hospitalZone.getZoneId())).thenReturn(hospitalZone);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);
		assertThrows(NoSuchAdminException.class, () -> {
			adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		});
	}
	/* Test Case 8:-> for addHospital with NoSuchTypeException */

	@Test
	void addHospitalTestWithNoSuchTypeException()
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin.setAdminId(1);
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		hospitalType.setTypeId(1);
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Gujarat");
		hospital.setHospitalName("jairam hospital");
		hospital.setHospitalId(1);
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);
		when(typeRepository.findHospitalTypeById(hospitalType.getTypeId())).thenReturn(null);
		when(zoneRepository.findHospitalZoneById(hospitalZone.getZoneId())).thenReturn(hospitalZone);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);
		assertThrows(NoSuchTypeException.class, () -> {
			hospital.setHospitalName("Kaushik hospital");
			adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		});
	}
	/* Test Case 9:-> for addHospital with NoSuchZoneException */

	@Test
	void addHospitalTestWithNoSuchZoneException()
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin.setAdminId(1);
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		hospitalType.setTypeId(1);
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Gujarat");
		hospital.setHospitalName("jairam hospital");
		hospital.setHospitalId(1);
		when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);
		when(typeRepository.findHospitalTypeById(hospitalType.getTypeId())).thenReturn(hospitalType);
		when(zoneRepository.findHospitalZoneById(hospitalZone.getZoneId())).thenReturn(null);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);
		assertThrows(NoSuchZoneException.class, () -> {
			hospital.setHospitalName("Kaushik hospital");
			adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());// test
																														// case
																														// no.6
		});
	}

	/* Test Case 9:-> for removeHospitalById */
	@Test
	void TestRemoveHospitalById() throws NoSuchAdminException, NoSuchHospitalException, NoSuchTypeException,
			NoSuchZoneException, AdminException {
		admin.setAdminId(1);
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKK@1223");
		admin.setAdminUserName("Jaijai");
		hospitalType.setTypeId(1);
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneId(1);
		hospitalZone.setZoneName("Gujarat");
		hospital.setHospitalName("jairam hospital");
		hospital.setHospitalId(1);	
		List<Admin> admins=new ArrayList<Admin>();
		admins.add(admin);
		hospital.setAdmins(admins);
		when(hospitalRepository.findByHospitalId(hospital.getHospitalId())).thenReturn(hospital);
		when(adminRepository.save(admin)).thenReturn(admin);
		when(typeRepository.save(hospitalType)).thenReturn(hospitalType);
		when(zoneRepository.save(hospitalZone)).thenReturn(hospitalZone);
		doNothing().when(hospitalRepository).delete(hospital);
		boolean value=adminService.removeHospitalById(hospital.getHospitalId());
		assertTrue(value);
	}
	/* Test Case 9:-> for removeHospitalById for NoSuchHospitalException */

	@Test
	void TestRemoveHospitalByIdThrowsNoSuchHospitalException() throws NoSuchAdminException, NoSuchHospitalException {
		hospital.setHospitalName("jairam hospital");
		hospital.setHospitalId(1);
		when(hospitalRepository.findByHospitalId(hospital.getHospitalId())).thenReturn(null);

		assertThrows(NoSuchHospitalException.class, () -> {
			adminService.removeHospitalById(hospital.getHospitalId());

		});

	}
	/* Test Case 9:-> for assignHospitalTo Admin for NoSuchHospitalException */

	@Test
	void assignHospitalToAdminTest()
			throws AdminException, NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");

		hospital = adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), // test case no.6
				hospitalType.getTypeId());
		Admin newAdmin = new Admin();
		newAdmin.setAdminFirstName("suraj");
		newAdmin.setAdminLastName("jain");
		newAdmin.setAdminPassword("KKKKii@1223");
		newAdmin.setAdminUserName("Lokesh222");
		List<Hospital> hpList = new ArrayList<>();
		newAdmin.setHospitals(hpList);
		adminService.addAdmin(newAdmin);
		adminService.assignHospitalToAdmin(hospital.getHospitalId(), newAdmin.getAdminId());
		boolean value = newAdmin.getHospitals().contains(hospital);
		assertTrue(value);
	}
}
