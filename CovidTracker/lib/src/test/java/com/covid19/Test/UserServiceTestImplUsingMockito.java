package com.covid19.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.covid19.model.Hospital;
import com.covid19.repository.StatusRepository;
import com.covid19.service.UserService;

@SpringBootTest
 class UserServiceTestImplUsingMockito {
	@Autowired
	UserService userService;
	
	@MockBean
	StatusRepository statusRepository;

	@Test
	 void findTotalCases() {
	
		when(statusRepository.findTotalCases()).thenReturn(5);
		assertEquals(5,userService.findTotalCases());
	}
	
	@Test
	 void findTotalLabTest() {
		when(statusRepository.findTotalLabTest()).thenReturn(5);
		assertEquals(5,userService.findTotalLabTest());
		
	}
	@Test
	 void findTotalConfirmedCases() {
		when(statusRepository.findTotalConfirmedCases()).thenReturn(5);
		assertEquals(5,userService.findTotalConfirmedCases());
		
	}
	
	@Test
	 void findTotalRecoveredCases() {
		when(statusRepository.findTotalRecoverdCases()).thenReturn(5);
		assertEquals(5,userService.findTotalRecoveredCases());
	}
	@Test
	 void findTotalPatientInIsolation() {
		when(statusRepository.findTotalPatientInIsolation()).thenReturn(5);
		assertEquals(5,userService.findTotalPatientInIsolation());
	}
	@Test
	 void findTotalDeath() {
		when(statusRepository.findTotalDeath()).thenReturn(5);
		assertEquals(5,userService.findTotalDeath());
	}
	
	@Test
	 void findTotalCasesIn24Hrs() {
		when(statusRepository.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1))).thenReturn(5);
		assertEquals(5,userService.findTotalCasesIn24Hrs(LocalDate.now(), LocalDate.now().minusDays(1)));
	}
	
	@Test
	 void findTotalDataBasedOnZoneAndDate() {
	
//		List<String> list = new ArrayList<>();
//		when(statusRepository.findTotalDataBasedOnZoneAndDate()).thenReturn(5);
	}
	
}
