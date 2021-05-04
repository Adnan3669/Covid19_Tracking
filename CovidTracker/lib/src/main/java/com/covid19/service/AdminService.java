package com.covid19.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.covid19.entities.Admin;
import com.covid19.entities.Hospital;
import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;

public interface AdminService {
	public boolean removeHospitalById(@Positive int hospitalId) throws NoSuchHospitalException, NoSuchAdminException;

	public Admin getAdminById(@Positive int adminId) throws NoSuchAdminException;

	public Hospital getHospitalById(@Positive int hospitalId) throws NoSuchHospitalException;

	public Admin addAdmin(@Valid Admin admin) throws AdminException;

	public boolean assignHospitalToAdmin(@Positive int hospitalId, @Positive int adminId);

	public List<Admin> getAllAdmins();

	public Hospital addHospital(@Positive int adminId, @Valid Hospital hospital, @Positive int hospitalZoneId,
			@Positive int hospitalTypeId) throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException;

	public Hospital modifyHospital(@Valid Hospital hospital) throws NoSuchHospitalException;

}
