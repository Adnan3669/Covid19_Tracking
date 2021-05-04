package com.covid19.service;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid19.controller.DeathPatientController;
import com.covid19.model.Hospital;
import com.covid19.model.HospitalZone;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;

@Service
public class DeathPatientServiceImpl implements DeathPatientService {

	@Autowired
	StatusRepository statusRepository;
	@Autowired
	HospitalZoneRepositary hospitalZoneRepositary;
	
	Logger logger = LoggerFactory.getLogger(DeathPatientController.class);


	/*
	 * Methods to find the total deaths according to different needs and scenarios
	 */

	@Override
	public int findMonthWiseDeath(@Min(1) @Max(12) int month) {
		logger.info("For finding total number of Deaths in a Month");
		LocalDate firstDay = LocalDate.of(LocalDate.now().getYear(), month, 1);
		LocalDate lastDay = LocalDate.of(LocalDate.now().getYear(), month, firstDay.lengthOfMonth());
		return statusRepository.findTotalDeathOfMonth(firstDay, lastDay);

	}

	@Override
	public int findDivisonWiseDeath(@NotNull String hospitalZone) {
		logger.info("For finding total number of Deaths based on Division");

		HospitalZone zone = hospitalZoneRepositary.findHospitalZoneByName(hospitalZone);
		return statusRepository.findTotalDeathByZone(zone.getZoneId());

	}

	@Override
	public int findAgeWiseDeath(@Positive int age) {
		logger.info("For finding total number of Deaths based on Age");

		return statusRepository.findTotalDeathByAge(age);
	}

	@Override
	public int findGenderWiseDeath(@Pattern(regexp = "^Male?$|^Female?$") String gender) {
		logger.info("For finding total number of Deaths based on Gender");

		return statusRepository.findTotalDeathByGender(gender);
	}

}
