package com.covid19.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.service.AdminService;
import com.covid19.util.AdminCredentials;

@SpringBootTest
public class AdminServiceTestImplUsingMockito {
	@Autowired
	AdminService adminService;
	@MockBean
	HospitalRepository hospitalRepository;
	@MockBean
	AdminRepository adminRepository;
	@MockBean
	AdminCredentials adminCredentials;
	@Autowired
	Hospital hospital;
	@Autowired
	Admin admin;

	@Test
	public void TestAddHospital() throws NoSuchAdminException {

		hospital.setHospitalId(1);
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		when(adminCredentials.adminCredentialsDetail()).thenReturn(admin);
		when(hospitalRepository.save(hospital)).thenReturn(hospital);
		when(adminRepository.save(admin)).thenReturn(admin);
		
		Hospital expectedHospital = adminService.addHospital(1,hospital);

		assertEquals(hospital, expectedHospital);
	}

	@Test
	public void GetHospitalById() throws NoSuchHospitalException, NoSuchAdminException {
		when(hospitalRepository.findByHospitalId(hospital.getHospitalId())).thenReturn(hospital);
		Hospital expectedHospital = adminService.getHospitalById(hospital.getHospitalId());
		assertEquals(expectedHospital, hospital);
	}

	@Test
	public void GetHospitalByIdForNoSuchHospitalException() throws NoSuchHospitalException {
		when(adminRepository.findByAdminId(-1)).thenReturn(null);
		assertThrows(NoSuchHospitalException.class, () -> {

			adminService.getHospitalById(-1);
		});

	}

	@Test
	public void TestRemoveHospitalById() throws NoSuchAdminException, NoSuchHospitalException {
		hospital.setHospitalId(1);
		hospital.setHospitalGeneralBed(10);
		hospital.setHospitalICUBed(10);
		hospital.setHospitalName("Jairam Hospital");
		admin.getHospitals().add(hospital);
		when(adminCredentials.adminCredentialsDetail()).thenReturn(admin);
		when(adminRepository.save(admin)).thenReturn(null);
		doNothing().when(hospitalRepository).deleteById(hospital.getHospitalId());

		boolean flag = adminService.removeHospitalById(hospital.getHospitalId());
		assertTrue(flag);

	}

	@Test
	public void TestRemoveHospitalByIdThrowsNoSuchHospitalException()
			throws NoSuchAdminException, NoSuchHospitalException {
		when(adminCredentials.adminCredentialsDetail()).thenReturn(admin);
		assertThrows(NoSuchHospitalException.class, () -> {
			adminService.removeHospitalById(-1);
		});

	}
}
