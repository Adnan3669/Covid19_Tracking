package com.covid19.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.service.AdminService;

@RestController
@RequestMapping("admin")
@Validated
public class AdminController {
	@Autowired
	AdminService service;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@PostMapping(path = "/addAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Admin addAdmin(@Valid Admin admin) throws AdminException {
		logger.info("For  creation of new Admin");
		return service.addAdmin(admin);
	}

	@PostMapping(path = "/addHospital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hospital addHospital(@RequestParam("adminId") @Positive int adminId, @RequestBody @Valid Hospital hospital)
			throws NoSuchAdminException {
		logger.info("For creation of a new HOSPITAL");

		return service.addHospital(adminId, hospital);
	}

	@DeleteMapping(path = "/removeHospital")
	public ResponseEntity<String> deleteHospital(@RequestParam(name = "id") @Positive int hospitalId)
			throws NoSuchAdminException, NoSuchHospitalException {
		logger.info("For deleting an existing HOSPITAL");
		if (service.removeHospitalById(hospitalId))
			return new ResponseEntity<>("Deletion Request Accepted Successfully", HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<>("Deletion Request was Unsucessful", HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(path = "/assignHospitalToAdmin")
	public ResponseEntity<String> assignHospitalToAdmin(@RequestParam("hospitalId") @Positive int hospitalId,
			@RequestParam("hospitalId") @Positive int adminId) {
		logger.info("For Assigning do the given hospital");
		if (service.assignHospitalToAdmin(hospitalId, adminId))
			return new ResponseEntity<>("Updated Content Successfully", HttpStatus.CREATED);
		else {
			return new ResponseEntity<>("Unsuccesful request", HttpStatus.NO_CONTENT);
		}
	}

}
