package com.covid19.service;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;

public interface AdminService {
	public boolean removeHospitalById(@Positive int hospitalId) throws NoSuchHospitalException, NoSuchAdminException;

	public Admin findAdminById(@Positive int adminId) throws NoSuchAdminException;

	public Hospital getHospitalById(@Positive int hospitalId) throws NoSuchHospitalException;

	public Hospital addHospital(@Positive int adminId, @Valid Hospital hospital) throws NoSuchAdminException;

	public Admin addAdmin(Admin admin) throws AdminException;
	public boolean  assignHospitalToAdmin(int hospitalId,int adminId);

}
