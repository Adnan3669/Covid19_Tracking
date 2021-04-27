package com.covid19.service;

import java.util.List;

import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.Type;
import com.covid19.model.Zone;

public interface HospitalService {
	public Hospital modifyHospital(Hospital hospital);

	public List<Hospital> findAllHospitals();

	public List<Hospital> findHospitalByType(Type type) throws NoSuchTypeException;

	public List<Hospital> findHospitalByZone(Zone zone) throws NoSuchZoneException;
	
	public List<Hospital> findHospitalByFreeBeds();

}
