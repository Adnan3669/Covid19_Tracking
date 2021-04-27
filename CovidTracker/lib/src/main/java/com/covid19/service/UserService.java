package com.covid19.service;

public interface UserService {
	public int findTotalCases();
	public int findTotalCasesIn24Hrs();
	public int findTotalLabTest();
	public int findTotalConfirmedCases();
	public int findTotalRecoveredCases();
	public int findTotalPatientInIsolation();
	public int findTotalDeath();
	 
}
