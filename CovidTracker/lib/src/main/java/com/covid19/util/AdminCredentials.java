package com.covid19.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.model.Admin;
import com.covid19.service.AdminService;

@Component
public class AdminCredentials {

	@Autowired
	AdminService service;
	public Admin adminCredentialsDetail() throws NoSuchAdminException {
		return service.findAdminById(1);
		
	}
}
