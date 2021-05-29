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

import com.covid19.entities.Admin;
import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalType;
import com.covid19.entities.HospitalZone;
import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalTypeRepositary;
import com.covid19.repository.HospitalZoneRepositary;

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
	HospitalZoneRepositary zoneRepository;
	@Autowired
	HospitalTypeRepositary typeRepository;
	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	/* This is for addingAdmin takes Admin object as Parameter */
	@Override
	public Admin addAdmin(@Valid Admin admin) throws AdminException {
		logger.info("Admin Adding");
		Admin findAdmin = adminRepository.findByAdminId(admin.getAdminId());
		if (findAdmin == null) {
			return adminRepository.save(admin);
		} 
		logger.warn("Entered in Admin Exception in AddAdmin");
		throw new AdminException("Admin Already Exists");
	}

	/*
	 * This is for getAllAdmin
	 * 
	 * @return List<Admin>
	 */
	@Override
	public List<Admin> getAllAdmins() {
		logger.info("GettingAll Admins");
		return adminRepository.findAll();
	}
	/*
	 * This is for assignHospitalToAdmin takes hospitalId and adminId
	 * 
	 * @return boolean
	 */
	@Override
	public boolean assignHospitalToAdmin(@Positive int hospitalId, @Positive int adminId) {
		logger.info("Assigning Admin to Hospital");
		Admin admin = adminRepository.findByAdminId(adminId);
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital != null && admin != null) {
			admin.getHospitals().add(hospital);
			adminRepository.save(admin);
			hospital.getAdmins().add(admin);
			hospitalRepository.save(hospital);
			return true;
		} else {
			logger.warn("logger info ");
			return false;
		}
	}
	/*
	 * This is for getHospitalById takes hospitalId 
	 * 
	 * @return Hospital
	 */
	@Override
	public Hospital getHospitalById(@Positive int hospitalId) throws NoSuchHospitalException {
		logger.info("Finding Hospital By Hospital Id");
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital == null)
			throw new NoSuchHospitalException("No Such Hospital Exist");
		return hospital;
	}
	/*
	 * This is for addHsopital takes hospitalId 
	 * 
	 * @return Hospital
	 */
	@Override
	public Hospital addHospital(@Positive int adminId, @Valid Hospital hospital, @Positive int hospitalZoneId,
			@Positive int hospitalTypeId) throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		logger.info("For creating HOSPITAL");
		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null)
			throw new NoSuchAdminException("No Such Admin Exists");
		List<Admin> adminList = new ArrayList<>();
		adminList.add(admin);
		hospital.setAdmins(adminList);
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
		
		hospital = hospitalRepository.save(hospital);
		admin.getHospitals().add(hospital);
		adminRepository.save(admin);
		return hospital;
	}
	/*
	 * This is for removeHsopital takes hospitalId 
	 * 
	 * @return boolean
	 */
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
				if (type.getHospitals() != null)
					type.getHospitals().remove(hospital);
				typeRepository.save(type);
			}
			HospitalZone zone = hospital.getHospitalZone();
			if (zone != null) {
				if (zone.getHospitals() != null)
					zone.getHospitals().remove(hospital);
				zoneRepository.save(zone);
			}
			hospitalRepository.delete(hospital);
			return true;
		}
		throw new NoSuchHospitalException("No Such Hospital Exists");

	}
	/*
	 * This is for getAdminById takes adminId 
	 * 
	 * @return Admin
	 */
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
	@Override
	public Admin getAdminCredentials(String username, String password) throws NoSuchAdminException {
		Admin admin=adminRepository.findAdminCredentials(username, password);
		if (admin == null) {
			throw new NoSuchAdminException("No Such Admin exists");
		}
		
		return admin;
	}

}