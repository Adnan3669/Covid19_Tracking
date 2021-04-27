package com.covid19.service;

import com.covid19.model.Hospital;

public interface AdminService{
	public Hospital addHospital(Hospital hospital);
	public boolean removeHospitalById(int hospitalId);
}
