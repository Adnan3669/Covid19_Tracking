package com.covid19.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.covid19.exceptions.AdminException;
import com.covid19.exceptions.NoSuchAdminException;
import com.covid19.exceptions.NoSuchHospitalException;
import com.covid19.exceptions.NoSuchTypeException;
import com.covid19.exceptions.NoSuchZoneException;
import com.covid19.model.Admin;
import com.covid19.model.Hospital;
import com.covid19.service.AdminService;

@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	/*
	 * Request for adding Admin
	 * http://localhost:9090/CovidTracker.com/admin/addAdmin
	 */
	@PostMapping(path = "/addAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Admin addAdmin(@Valid @RequestBody Admin admin) throws AdminException {
		logger.info("For  creation of new Admin");
		return adminService.addAdmin(admin);
	}

	/*
	 * Request for adding Hospital
	 * http://localhost:9090/CovidTracker.com/admin/addHospital
	 */
	@PostMapping(path = "/addHospital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hospital addHospital(@RequestParam("adminId") @Positive int adminId, @RequestBody @Valid Hospital hospital,
			@RequestParam("zoneId") @Positive int hospitalZoneId, @Positive @RequestParam("typeId") int hospitalTypeId)
			throws NoSuchAdminException, NoSuchTypeException, NoSuchZoneException {
		logger.info("For creation of a new HOSPITAL");
		return adminService.addHospital(adminId, hospital, hospitalZoneId, hospitalTypeId);
	}

	/*
	 * Request for removing Hospital
	 * http://localhost:9090/CovidTracker.com/admin/removeHospital
	 */
	@DeleteMapping(path = "/removeHospital")
	public ResponseEntity<String> deleteHospital(@RequestParam(name = "id") @Positive int hospitalId)
			throws NoSuchAdminException, NoSuchHospitalException {
		logger.info("For deleting an existing HOSPITAL");
		if (adminService.removeHospitalById(hospitalId))
			return new ResponseEntity<>("Deletion Request Accepted Successfully", HttpStatus.ACCEPTED);
		else {
			return new ResponseEntity<>("Deletion Request was Unsucessful", HttpStatus.NO_CONTENT);
		}
	}

	/*
	 * Request for assignHospitalToAdmin
	 * http://localhost:9090/CovidTracker.com/admin/assignHospitalToAdmin
	 */
	@PutMapping(path = "/assignHospitalToAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assignHospitalToAdmin(@RequestParam("hospitalId") @Positive int hospitalId,
			@RequestParam("adminId") @Positive int adminId) {
		logger.info("For Assigning do the given hospital");
		if (adminService.assignHospitalToAdmin(hospitalId, adminId))
			return new ResponseEntity<>("Updated Content Successfully", HttpStatus.CREATED);
		else {
			return new ResponseEntity<>("Unsuccesful request", HttpStatus.NO_CONTENT);
		}
	}

	/*
	 * Request for modifyHospital
	 * http://localhost:9090/CovidTracker.com/admin/modifyHospital
	 */
	@PutMapping(path = "/modifyHospital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Hospital> modifyHospital(@RequestBody @Valid Hospital hospital)
			throws NoSuchHospitalException {
		logger.info("For modifying HOSPITAL details");
		ResponseEntity<Hospital> response = null;
		Hospital result = adminService.modifyHospital(hospital);
		if (result != null)
			response = new ResponseEntity<>(result, HttpStatus.OK);
		else
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return response;
	}

	/*
	 * Request for allAdmins http://localhost:9090/CovidTracker.com/admin/allAdmins
	 */
	@GetMapping(path = "/allAdmins", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return new ResponseEntity<>(adminService.getAllAdmins(), HttpStatus.ACCEPTED);
	}

	/*
	 * Request for getAdminById
	 * http://localhost:9090/CovidTracker.com/admin/getAdmin
	 */
	@GetMapping(path = "/getAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> getAdminById(@RequestParam("adminId") @Positive int adminId)
			throws NoSuchAdminException {
		return new ResponseEntity<>(adminService.getAdminById(adminId), HttpStatus.ACCEPTED);
	}

	/*
	 * Request for getHospital
	 * http://localhost:9090/CovidTracker.com/admin/getHospital
	 */
	@GetMapping(path = "/getHospital", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hospital> getHospitalById(@RequestParam("hospitalId") @Positive int hospitalId)
			throws NoSuchHospitalException {
		return new ResponseEntity<>(adminService.getHospitalById(hospitalId), HttpStatus.ACCEPTED);
	}

}
