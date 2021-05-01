package com.covid19.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;


public interface UserService {
	public int findTotalCases();

	public int findTotalLabTest();
	public int findTotalConfirmedCases() ;
	public int findTotalRecoveredCases();
	public int findTotalPatientInIsolation();
	public int findTotalDeath();
	public int findTotalCasesIn24Hrs(LocalDate currentDate , LocalDate PassedDate);

	public List<String> findTotalDataBasedOnZoneAndDate(@Pattern(regexp = "[A-Za-z0-9]+$") @NotNull String zoneName,@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date);
	   
	
	 }