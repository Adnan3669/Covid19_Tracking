package com.covid19.service;

import java.util.List;

import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;

public interface HospitalService {
	public Hospital modifyHospital(Hospital hospital);

	public List<Hospital> findAllHospitals();

	public List<Hospital> findHospitalByType(String type) throws NoSuchTypeException;

	public List<Hospital> findHospitalByZone(String zone) throws NoSuchZoneException;
	
	public List<Hospital> findHospitalByFreeBeds();

	HospitalType addHospitalType(HospitalType hospitalType);

	HospitalZone addHospitalZone(HospitalZone hospitalZone);

	

}