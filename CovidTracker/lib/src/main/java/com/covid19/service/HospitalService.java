package com.covid19.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;

public interface HospitalService {
	public Hospital modifyHospital(@Valid Hospital hospital) throws NoSuchHospitalException;

	public List<Hospital> findAllHospitals();

	public List<Hospital> findHospitalByType(@Pattern(regexp = "[A-Za-z0-9]+$") String type) throws NoSuchTypeException;

	public List<Hospital> findHospitalByZone(@Pattern(regexp = "[A-Za-z]+$") String zone) throws NoSuchZoneException;
	
	public List<Hospital> findHospitalByFreeBeds();

	HospitalType addHospitalType(@Valid HospitalType hospitalType);

	HospitalZone addHospitalZone(@Valid HospitalZone hospitalZone);

	

}