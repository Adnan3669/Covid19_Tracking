package com.covid19.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;
import com.covid19.util.AdminCredentials;

@Service
@Scope("singleton")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private HospitalRepository hospitalRepository;
	@Autowired
	AdminCredentials adminCredentials;

	@Override
	public Hospital getHospitalById(int hospitalId) throws NoSuchHospitalException {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
		if (hospital == null)
			throw new NoSuchHospitalException("No Such Hospital Exist");
		return hospital;
	}

	@Override
	public Hospital addHospital(Hospital hospital) throws NoSuchAdminException {
		Admin admin = adminCredentials.adminCredentialsDetail();
		
		hospitalRepository.save(hospital);
		admin.getHospitals().add(hospital);
		adminRepository.save(admin);
		return hospital;
	}

	@Override
	public boolean removeHospitalById(int hospitalId) throws NoSuchHospitalException, NoSuchAdminException {

		Admin admin = adminCredentials.adminCredentialsDetail();

		Iterator<Hospital> hospitals = admin.getHospitals().iterator();
		int index = -1;
		while (hospitals.hasNext()) {
			Hospital hospital = hospitals.next();
			System.out.println(hospital);
			if (hospital.getHospitalId() == hospitalId) {

				index = admin.getHospitals().indexOf(hospital);
			}
		}
		if (index != -1) {
			admin.getHospitals().remove(index);
			adminRepository.save(admin);
			hospitalRepository.deleteById(hospitalId);
			return true;
		}
		throw new NoSuchHospitalException("No Such Hospital Exists");
	}

	@Override
	public Admin findAdminById(int adminId) throws NoSuchAdminException {
		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null) {
			throw new NoSuchAdminException("No Such Admin exists");
		}
		return admin;
	}

}
