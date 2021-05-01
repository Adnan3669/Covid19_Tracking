package com.covid19.service;

import java.time.LocalDate;
import java.util.List;


public interface UserService {
	public int findTotalCases();

	public int findTotalLabTest();
	public int findTotalConfirmedCases() ;
	public int findTotalRecoveredCases();
	public int findTotalPatientInIsolation();
	public int findTotalDeath();
	public int findTotalCasesIn24Hrs(LocalDate currentDate , LocalDate PassedDate);

	List<String> findTotalDataBasedOnZoneAndDate(String zoneName, LocalDate date);
	
	 }