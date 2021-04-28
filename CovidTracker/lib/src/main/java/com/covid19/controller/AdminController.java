package com.covid19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Hospital;
import com.covid19.service.AdminService;

@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	AdminService service;
	
	@PostMapping(path = "/addHospital",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Hospital addHospital(@RequestBody Hospital hospital) throws NoSuchAdminException
	{
		return service.addHospital(hospital);
	}
	@DeleteMapping(path = "/removeHospital",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addHospital(@RequestParam(name = "id") int hospitalId) throws NoSuchAdminException, NoSuchHospitalException
	{
		return service.removeHospitalById(hospitalId);
	}
	@ExceptionHandler
	public String ExceptionHandler(NoSuchAdminException e)
	{
		return e.getMessage();
	}
	@ExceptionHandler
	public String ExceptionHandler(NoSuchHospitalException e)
	{
		return e.getMessage();
	}
}
