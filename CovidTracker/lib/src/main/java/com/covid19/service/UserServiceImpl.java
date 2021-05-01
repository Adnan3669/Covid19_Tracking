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

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	List<String> list;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private HospitalZoneRepositary hospitalZoneRepositary;

	@Override
	public int findTotalCases() {

		return statusRepository.findTotalCases();
	}

	@Override
	public int findTotalCasesIn24Hrs(LocalDate currentDate, LocalDate passedDate) {
		return statusRepository.findTotalCasesIn24Hrs(currentDate, passedDate);
	}

	@Override
	public int findTotalLabTest() {
		return statusRepository.findTotalLabTest();
	}

	@Override
	public int findTotalConfirmedCases() {
		return statusRepository.findTotalConfirmedCases();
	}

	@Override
	public int findTotalRecoveredCases() {
		return statusRepository.findTotalRecoverdCases();
	}

	@Override
	public int findTotalPatientInIsolation() {
		return statusRepository.findTotalPatientInIsolation();
	}

	@Override
	public int findTotalDeath() {
		return statusRepository.findTotalDeath();
	}
	@Override
	public List<String> findTotalDataBasedOnZoneAndDate(@Pattern(regexp = "[A-Za-z0-9]+$") @NotNull String zoneName,@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date)
	{
		Integer death=0,active=0,recovered=0,confirm=0;
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