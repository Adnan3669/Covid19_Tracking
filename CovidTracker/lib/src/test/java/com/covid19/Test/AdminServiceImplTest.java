package com.covid19.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.service.AdminService;
import com.covid19.service.HospitalService;

@Transactional
@SpringBootTest
class AdminServiceImplTest {

	@Autowired
	AdminService adminService;
	@Autowired
	Hospital hospital;
	@Autowired
	Admin admin;
	@Autowired
	AdminRepository adminRepository;

	@Autowired
	HospitalService hospitalService;
	@Autowired
	HospitalRepository hospitalRepository;
	@Autowired
	HospitalType hospitalType;
	@Autowired
	HospitalZone hospitalZone;

	@BeforeEach
	void setup() {
		admin.setAdminFirstName("gaurav");
		admin.setAdminLastName("Kailash");
		admin.setAdminPassword("KKKKk1223");
		admin.setAdminUserName("Jaijai");
		List<Hospital> hpList = new ArrayList<>();
		admin.setHospitals(hpList);

	}

	/* Test Case:1-> for AddAdmin function */
	@Test
	void addAdminTest() throws AdminException {
		Admin expectedAdmin = adminService.addAdmin(admin);
		Admin actualAdmin = adminRepository.findByAdminId(admin.getAdminId());
		assertEquals(actualAdmin, expectedAdmin);
	}

	/* Test Case:2 -> for AddAdmin with AdminExceptionTest function */
	@Test
	void addAdminWithAdminExceptionTest() throws AdminException, NoSuchAdminException {
		assertThrows(AdminException.class, () -> {
			Admin actualAdmin = adminRepository.save(admin);
			adminService.addAdmin(actualAdmin);

		});

	}

	/* Test Case 3:-> for getAdminByAdminTest */
	@Test
	void getAdminByAdminIdTest() throws AdminException, NoSuchAdminException {
		Admin expectedAdmin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		Admin actualAdmin = adminService.getAdminById(admin.getAdminId());
		assertEquals(expectedAdmin, actualAdmin);
	}

	/* Test Case 4:-> for getAdminByAdminTest with NoSuchAdminException function */
	@Test
	void getAdminByAdminIdWithNoSuchAdminExceptionTest() throws AdminException, NoSuchAdminException {

		assertThrows(NoSuchAdminException.class, () -> {
			adminService.getAdminById(90000);

		});
	}

	/* Test Case 5:-> for getAllAdmins */
	@Test
	void getAllAdminsTest() throws AdminException {
		adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		Boolean value = adminService.getAllAdmins().contains(admin);
		assertTrue(value);
	}

	/* Test Case 6:-> for addHospital */

	@Test
	void addHospitalTest() throws AdminException, NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);// This test function on HospitalServiceImplTestClass
		hospital.setHospitalName("jairam hospital");
		Hospital expectedHospital = adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(),
				hospitalType.getTypeId());
		Hospital actualHospital = hospitalRepository.findByHospitalId(hospital.getHospitalId());
		assertEquals(actualHospital, expectedHospital);
	}

	/* Test Case 7:-> for addHospital with NoSuchAdminException */
	@Test
	void addHospitalTestWithNoSuchAdminException()
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);// This test function on HospitalServiceImplTestClass
		assertThrows(NoSuchAdminException.class, () -> {
			hospital.setHospitalName("Kaushik hospital");
			adminService.addHospital(10000, hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());
		});
	}
	/* Test Case 8:-> for addHospital with NoSuchTypeException */

	@Test
	void addHospitalTestWithNoSuchTypeException()
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);
		assertThrows(NoSuchTypeException.class, () -> {
			hospital.setHospitalName("Kaushik hospital");
			adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), 10000);
		});
	}
	/* Test Case 9:-> for addHospital with NoSuchZoneException */

	@Test
	void addHospitalTestWithNoSuchZoneException()
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException, AdminException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);
		assertThrows(NoSuchZoneException.class, () -> {
			hospital.setHospitalName("Kaushik hospital");
			adminService.addHospital(admin.getAdminId(), hospital, 1000, hospitalType.getTypeId());// test case no.6
		});
	}

	/* Test Case 9:-> for removeHospitalById */
	@Test
	void TestRemoveHospitalById() throws NoSuchAdminException, NoSuchHospitalException, NoSuchTypeException,
			NoSuchZoneException, AdminException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		List<Admin> admins = new ArrayList<>();
		hospital.setAdmins(admins);
		adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), hospitalType.getTypeId());// test
																														// case
																														// no.6
		boolean flag = adminService.removeHospitalById(hospital.getHospitalId());
		assertTrue(flag);

	}
	/* Test Case 9:-> for removeHospitalById for NoSuchHospitalException */

	@Test
	void TestRemoveHospitalByIdThrowsNoSuchHospitalException() throws NoSuchAdminException, NoSuchHospitalException {
		assertThrows(NoSuchHospitalException.class, () -> {
			adminService.removeHospitalById(10000);

		});

	}
	/* Test Case 9:-> for assignHospitalTo Admin for NoSuchHospitalException */

	@Test
	void assignHospitalToAdminTest()
			throws AdminException, NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);// This test function on HospitalServiceImplTestClass
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		List<Admin> admins = new ArrayList<>();
		hospital.setAdmins(admins);
		hospital = adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(), // test case no.6
				hospitalType.getTypeId());
		Admin newAdmin = new Admin();
		newAdmin.setAdminFirstName("suraj");
		newAdmin.setAdminLastName("jain");
		newAdmin.setAdminPassword("KKKKii1223");
		newAdmin.setAdminUserName("Lokesh222");
		List<Hospital> hpList = new ArrayList<>();
		newAdmin.setHospitals(hpList);
		adminService.addAdmin(newAdmin);
		adminService.assignHospitalToAdmin(hospital.getHospitalId(), newAdmin.getAdminId());
		boolean value = newAdmin.getHospitals().contains(hospital);
		assertTrue(value);
	}
	/* Test Case 10:-> for ModifyHospital */

	@Test
	void modifyHospitalTest() throws AdminException, NoSuchAdminException, NoSuchTypeException, NoSuchZoneException,
			NoSuchHospitalException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);// This test function on HospitalServiceImplTestClass
		hospital.setHospitalName("jairam hospital");
		Hospital expectedHospital = adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(),
				hospitalType.getTypeId());// test case no.6
		hospital.setHospitalName("Kushki Hospital");
		adminService.modifyHospital(expectedHospital);
		assertNotEquals("jairam hospital", expectedHospital.getHospitalName());
	}
	/* Test Case 11:-> for ModifyHospital for NoSuchHospitalException */

	@Test
	void modifyHospitalTestThrowsNoSuchHospitalException() throws AdminException, NoSuchAdminException,
			NoSuchTypeException, NoSuchZoneException, NoSuchHospitalException {
		admin = adminService.addAdmin(admin);/* Done Test ->Test Case No:1 */
		hospitalType.setTypeName("Corporate");
		hospitalZone.setZoneName("Gujarat");
		hospitalService.addHospitalType(hospitalType);// This test function on HospitalServiceImplTestClass
		hospitalService.addHospitalZone(hospitalZone);// This test function on HospitalServiceImplTestClass
		hospital.setHospitalName("jairam hospital");
		Hospital expectedHospital = adminService.addHospital(admin.getAdminId(), hospital, hospitalZone.getZoneId(),
				hospitalType.getTypeId());// test case no.6
		adminService.removeHospitalById(expectedHospital.getHospitalId());

		assertThrows(NoSuchHospitalException.class, () -> {
			adminService.modifyHospital(expectedHospital);

		});
	}
	/* Test Case 9:-> for Add Admin ConstraintViolationException */

	@Test
	void addAdminThrowsConstraintViolationExceptionTest() throws NoSuchAdminException, NoSuchHospitalException {
		assertThrows(ConstraintViolationException.class, () -> {
			admin.setAdminFirstName(null);
			adminService.addAdmin(admin);

		});

	}

}
