package com.covid19.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;

@Service
@Validated
@Scope("singleton")
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private HospitalTypeRepositary typeRepository;

	@Autowired
	private HospitalZoneRepositary zoneRepository;

	Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);



	@Override
	public List<Hospital> findAllHospitals() {
		logger.info("For finding all HOSPITal details");
		return hospitalRepository.findAll();
	}

	@Override
	public List<Hospital> findHospitalByType(@Pattern(regexp = "[A-Za-z]+$") String type) throws NoSuchTypeException {
		logger.info("For finding HOSPITAL by filtering type");
		if (typeRepository.findHospitalTypeByName(type) != null) {
			return hospitalRepository.findHospitalByType(type);
		} else {
			throw new NoSuchTypeException("No Such Type Exist");
		}
	}

	@Override
	public List<Hospital> findHospitalByZone(@Pattern(regexp = "[A-Za-z0-9]+$") String zone)
			throws NoSuchZoneException {
		logger.info("For finding HOSPITAL by filtering zone");
		if (zoneRepository.findHospitalZoneByName(zone) != null) {
			return hospitalRepository.findHospitalByZone(zone);
		} else {
			throw new NoSuchZoneException("No Such zone Exist");
		}
	}

	@Override
	public List<Hospital> findHospitalByFreeBeds() {
		logger.info("For finding HOSPITAL by filtering free beds");
		return hospitalRepository.findHospitalByFreeBeds();
	}

	@Override
	public HospitalType addHospitalType(@Valid HospitalType hospitalType) {
		logger.info("For adding HOSPITAL type");
		return typeRepository.save(hospitalType);
	}

	@Override
	public List<HospitalZone> getAllHospitalZones() {
		return zoneRepository.findAll();
	}

	@Override
	public List<HospitalType> getAllHospitalType() {
		return typeRepository.findAll();
	}

	@Override
	public HospitalZone addHospitalZone(@Valid HospitalZone hospitalZone) {
		logger.info("For adding HOSPITAL zone");
		return zoneRepository.save(hospitalZone);
	}

}