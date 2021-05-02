package com.covid19.service;

import java.util.Iterator;

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
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
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
	public Admin addAdmin(Admin admin) throws AdminException {
		Admin existingAdmin = adminRepository.findByAdminId(admin.getAdminId());
		if (existingAdmin == null) {
			return adminRepository.save(admin);
		} else
			throw new AdminException("Admin Already exist");
	}

	@Override
	public boolean assignHospitalToAdmin(int hospitalId, int adminId) {
		Admin admin = adminRepository.findByAdminId(adminId);
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital != null && admin != null) {
			admin.getHospitals().add(hospital);
			adminRepository.save(admin);
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

	@Override
	public Hospital addHospital(@Positive int adminId, @Valid Hospital hospital) throws NoSuchAdminException {
		logger.info("For creating HOSPITAL");
		Admin admin = adminRepository.findByAdminId(adminId);
		hospitalRepository.save(hospital);
		admin.getHospitals().add(hospital);
		adminRepository.save(admin);
		return hospital;
	}

	@Override
	public boolean removeHospitalById(@Positive int hospitalId) throws NoSuchHospitalException, NoSuchAdminException {
		logger.info("For deleting HOSPITAL using id");
		/*
		 * Iterator<Hospital> hospitals = admin.getHospitals().iterator(); int index =
		 * -1; while (hospitals.hasNext()) { Hospital hospital = hospitals.next(); if
		 * (hospital.getHospitalId() == hospitalId) {
		 * 
		 * index = admin.getHospitals().indexOf(hospital); } } if (index != -1) {
		 * admin.getHospitals().remove(index); adminRepository.save(admin);
		 * hospitalRepository.deleteById(hospitalId); return true; } throw new
		 * NoSuchHospitalException("No Such Hospital Exists");
		 */
		Hospital hospital=hospitalRepository.findByHospitalId(hospitalId);
		if(hospital!=null)
		{
			hospitalRepository.delete(hospital);
			return true;
		}
		else {
			throw new NoSuchHospitalException("No Such Hospital exists to remove.");
		}
	}

	@Override
	public Admin findAdminById(@Positive int adminId) throws NoSuchAdminException {
		logger.info("Finding Admin By Admin Id");
		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null) {
			throw new NoSuchAdminException("No Such Admin exists");
		}
		return admin;
	}

}
