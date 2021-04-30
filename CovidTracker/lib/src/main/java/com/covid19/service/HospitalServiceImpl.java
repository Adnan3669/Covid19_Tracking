package com.covid19.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;

@Service
@Scope("singleton")
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalRepository repository;
	
	@Autowired
	private HospitalTypeRepositary typeRepository;
	
	@Autowired
	private HospitalZoneRepositary zoneRepository;

	@Override
	public Hospital modifyHospital(Hospital hospital) {
		return repository.save(hospital);
	}

	@Override
	public List<Hospital> findAllHospitals() {
		return repository.findAll();
	}

	@Override
	public List<Hospital> findHospitalByType(String type) throws NoSuchTypeException {
		return repository.findHospitalByType(type);
	}

	@Override
	public List<Hospital> findHospitalByZone(String zone) throws NoSuchZoneException {
		return repository.findHospitalByZone(zone);
	}

	@Override
	public List<Hospital> findHospitalByFreeBeds() {
		return repository.findHospitalByFreeBeds();
	}
	
	
	@Override
	public HospitalType addHospitalType(HospitalType hospitalType) {
		return typeRepository.save(hospitalType);
	}
	
	@Override
	public HospitalZone addHospitalZone(HospitalZone hospitalZone) {
		return zoneRepository.save(hospitalZone);
	}
	

}