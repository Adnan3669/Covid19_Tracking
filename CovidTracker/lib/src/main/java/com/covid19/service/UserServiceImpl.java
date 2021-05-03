package com.covid19.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.covid19.model.Hospital;
import com.covid19.model.HospitalZone;
import com.covid19.model.Patient;
import com.covid19.model.Status;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;
/*
 * @Service It is used with classes that provide some business functionalities
 * @Autowired Spring framework enables automatic dependency injection
 * */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	List<String> list;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private HospitalZoneRepositary hospitalZoneRepositary;

	/*
	 * here we override findTotalCases() method.
	 * it will give the total cases and return type is integer
	 * */

	@Override
	public int findTotalCases() {

		return statusRepository.findTotalCases();
	}

	/*
	 * here we override findTotalCasesIn24Hrs() method.
	 * it will give the total cases in last 24 hours by passing two argument as current date and passDate
	 * return type is integer
	 * */
	@Override
	public int findTotalCasesIn24Hrs(LocalDate currentDate, LocalDate passedDate) {
		return statusRepository.findTotalCasesIn24Hrs(currentDate, passedDate);
	}


	/*
	 * here we override findTotalLabTest() method.
	 * it will give the total Lab Test and return type is integer
	 * */
	@Override
	public int findTotalLabTest() {
		return statusRepository.findTotalLabTest();
	}

	
	/*
	 * here we override findTotalConfirmedCases() method.
	 * it will give the total Confirmed Cases and return type is integer
	 * */
	@Override
	public int findTotalConfirmedCases() {
		return statusRepository.findTotalConfirmedCases();
	}

	/*
	 * here we override findTotalRecoveredCases() method.
	 * it will give the total Recovered Cases and return type is integer
	 * */
	@Override
	public int findTotalRecoveredCases() {
		return statusRepository.findTotalRecoverdCases();
	}
	
	/*
	 * here we override findTotalPatientInIsolation() method.
	 * it will give the total Patient In Isolation and return type is integer
	 * */
	@Override
	public int findTotalPatientInIsolation() {
		return statusRepository.findTotalPatientInIsolation();
	}

	/*
	 * here we override findTotalDeath() method.
	 * it will give the total Death and return type is integer
	 * */
	@Override
	public int findTotalDeath() {
		return statusRepository.findTotalDeath();
	}
	
	@Override
	public List<String> findTotalDataBasedOnZoneAndDate(@Pattern(regexp = "[A-Za-z0-9]+$") @NotNull String zoneName,@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date)
	{
		Integer death=0;
		Integer active=0;
		Integer recovered=0;
		Integer confirm=0;
		HospitalZone zone=hospitalZoneRepositary.findHospitalZoneByName(zoneName);
		for (Hospital hospital : zone.getHospitals()) {
			confirm+= statusRepository.findTotalCasesinParticularDate(date,hospital.getHospitalId());
			death+=statusRepository.findTotalDeathinParticularDate(date, hospital.getHospitalId());
			recovered+=statusRepository.findTotalRecoveredinParticularDate(date, hospital.getHospitalId());
			active+=statusRepository.findTotalActiveinParticularDate(date, hospital.getHospitalId());
		}
		
		list.add("Confirmed Cases :"+confirm);
		list.add("Active Cases: "+active);
		list.add("Recovered Cases: "+recovered);
		list.add("Death cases: " +death);
		return list;
		
	}

}