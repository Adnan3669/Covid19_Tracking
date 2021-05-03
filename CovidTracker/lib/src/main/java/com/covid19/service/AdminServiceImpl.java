package com.covid19.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalType;
import com.covid19.model.HospitalZone;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.util.AdminCredentials;

@Service
@Scope("singleton")
@Validated
public class AdminServiceImpl implements AdminService {
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private HospitalRepository hospitalRepository;
	@Autowired
	AdminCredentials adminCredentials;
	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public Admin addAdmin(@Valid Admin admin) throws AdminException {
		Admin existingAdmin = adminRepository.findByAdminId(admin.getAdminId());
		if (existingAdmin == null) {
			return adminRepository.save(admin);
		} else
			throw new AdminException("Admin Already exist");
	}

	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	@Override
	public boolean assignHospitalToAdmin(@Positive int hospitalId, @Positive int adminId) {
		Admin admin = adminRepository.findByAdminId(adminId);
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital != null && admin != null) {
			admin.getHospitals().add(hospital);
			adminRepository.save(admin);
			hospital.getAdmins().add(admin);
			hospitalRepository.save(hospital);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Hospital getHospitalById(@Positive int hospitalId) throws NoSuchHospitalException {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital == null)
			throw new NoSuchHospitalException("No Such Hospital Exist");
		return hospital;
	}

	@Autowired
	HospitalZoneRepositary zoneRepository;
	@Autowired
	HospitalTypeRepositary typeRepository;

	@Override
	public Hospital addHospital(@Positive int adminId, @Valid Hospital hospital, @Positive int hospitalZoneId,
			@Positive int hospitalTypeId) throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		logger.info("For creating HOSPITAL");
		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null)
			throw new NoSuchAdminException("No Such Admin Exists");
		List<Admin> adminList = new ArrayList<>();
		adminList.add(admin);
		HospitalType hospitalType = typeRepository.findHospitalTypeById(hospitalTypeId);
		if (hospitalType != null) {
			hospital.setHospitalType(hospitalType);
		} else {
			throw new NoSuchTypeException("No Such Type Exist first add Such Type");
		}
		HospitalZone hospitalZone = zoneRepository.findHospitalZoneById(hospitalZoneId);
		if (hospitalZone != null) {
			hospital.setHospitalZone(hospitalZone);
		} else {
			throw new NoSuchZoneException("No Such Zone Exist  first add Such Zone");
		}
		hospital.setAdmins(adminList);
		hospitalRepository.save(hospital);
		admin.getHospitals().add(hospital);
		adminRepository.save(admin);
		return hospital;
	}

	@Override
	public boolean removeHospitalById(@Positive int hospitalId) throws NoSuchHospitalException, NoSuchAdminException {
		logger.info("For deleting HOSPITAL using id");
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital != null) {
			for (Admin admin : hospital.getAdmins()) {
				admin.getHospitals().remove(hospital);
				adminRepository.save(admin);
			}
			HospitalType type = hospital.getHospitalType();
			if (type != null) {
				type.getHospitals().remove(hospital);
				typeRepository.save(type);
			}
			HospitalZone zone = hospital.getHospitalZone();
			if (zone != null) {
				zone.getHospitals().remove(hospital);
				zoneRepository.save(zone);
			}
			hospitalRepository.delete(hospital);
			return true;
		}
		throw new NoSuchHospitalException("No Such Hospital Exists");

	}

	@Override
	public Admin getAdminById(@Positive int adminId) throws NoSuchAdminException {
		logger.info("Finding Admin By Admin Id");
		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null) {
			throw new NoSuchAdminException("No Such Admin exists");
		}
		return admin;
	}

	@Override
	public Hospital modifyHospital(@Valid Hospital hospital) throws NoSuchHospitalException {
		logger.info("For modifying HOSPITAL");

		Hospital modifiableHospital = hospitalRepository.findByHospitalId(hospital.getHospitalId());
		if (modifiableHospital != null) {
			modifiableHospital.setHospitalGeneralBed(hospital.getHospitalGeneralBed());
			modifiableHospital.setHospitalICUBed(hospital.getHospitalICUBed());
			modifiableHospital.setHospitalName(hospital.getHospitalName());
			return hospitalRepository.save(modifiableHospital);

		} else {
			throw new NoSuchHospitalException("No Such Hospital Exist");
		}
	}
}