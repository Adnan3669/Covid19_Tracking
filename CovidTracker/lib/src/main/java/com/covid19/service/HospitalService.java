package com.covid19.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalType;
import com.covid19.entities.HospitalZone;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;

public interface HospitalService {

	public List<Hospital> findAllHospitals();

	public List<Hospital> findHospitalByType(@Pattern(regexp = "[A-Za-z]+$") String type) throws NoSuchTypeException;

	public List<Hospital> findHospitalByZone(@Pattern(regexp = "[A-Za-z0-9]+$") String zone) throws NoSuchZoneException;
	
	public List<Hospital> findHospitalByFreeBeds();

	HospitalType addHospitalType(@Valid HospitalType hospitalType);

	HospitalZone addHospitalZone(@Valid HospitalZone hospitalZone);

	public List<HospitalType> getAllHospitalType();

	List<HospitalZone> getAllHospitalZones();

	

}