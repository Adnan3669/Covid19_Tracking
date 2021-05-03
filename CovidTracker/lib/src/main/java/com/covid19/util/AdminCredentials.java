package com.covid19.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.model.Admin;
import com.covid19.repository.AdminRepository;

@Component
public class AdminCredentials {

	@Autowired
	AdminRepository adminRepository;
	public Admin adminCredentialsDetail() throws NoSuchAdminException {
		return adminRepository.findByAdminId(1);
		
	}
}
