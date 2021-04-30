package com.covid19.service;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;

public interface AdminService{
	public Hospital addHospital(Hospital hospital) throws NoSuchAdminException;
	public boolean removeHospitalById(int hospitalId) throws NoSuchHospitalException, NoSuchAdminException;
	public Admin findAdminById(int adminId) throws NoSuchAdminException;
	public Hospital getHospitalById(int hospitalId) throws NoSuchHospitalException;
}
