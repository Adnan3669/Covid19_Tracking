package com.covid19.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.repository.AdminRepository;
import com.covid19.repository.HospitalRepository;

@Service
@Scope("singleton")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private HospitalRepository hospitalRepository;
	@Override
	public Hospital addHospital(Hospital hospital) throws NoSuchAdminException {
		Admin admin=findAdminById(1);
		admin.getHospitals().add(hospital);
		adminRepository.save(admin);
		return hospital;
	}

	@Override
	public boolean removeHospitalById(int hospitalId) throws NoSuchHospitalException, NoSuchAdminException {
		try
		{
		
		}catch(IllegalArgumentException e)
		{
			throw new NoSuchHospitalException("No Such Hospital Exists");
		}
		return true;
		 
	}

	@Override
	public Admin findAdminById(int adminId) throws NoSuchAdminException {
		Admin admin=adminRepository.findByAdminId( adminId);
		if(admin==null)
		{
			throw new NoSuchAdminException("No Such Admin exists");
		}
		return admin;
	}

}
