package com.covid19.service;

import java.time.LocalDate;
import java.util.Map;

import com.covid19.entities.Record;


public interface UserService {
	public int findTotalCases();

	public int findTotalLabTest();
	public int findTotalConfirmedCases() ;
	public int findTotalRecoveredCases();
	public int findTotalPatientInIsolation();
	public int findTotalDeath();
	public int findTotalCasesIn24Hrs(LocalDate currentDate , LocalDate PassedDate);

	Record findTotalDataBasedOnZoneAndDate(int zoneId, LocalDate date);

	Map<Integer,Record> findTotalDataBasedOnMonth(int startMonth, int endMonth);

	Map<String, Record> findTotalDataBasedOnZone();
	
	 }