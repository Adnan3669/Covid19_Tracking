package com.covid19.service;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;

import com.covid19.model.HospitalZone;
import com.covid19.repository.StatusRepository;

public class DeathPatientServiceImpl implements DeathPatientService {

	@Autowired
	StatusRepository statusRepository;

	@Override
	public int findMonthWiseDeath(@Min(1) @Max(12) int month) {
		LocalDate firstDay = LocalDate.of(LocalDate.now().getYear(), month, 1);
		LocalDate lastDay = LocalDate.of(LocalDate.now().getYear(), month, 30);
		statusRepository.findTotalDeathOfMonth(firstDay,lastDay);
		return month;
	}

	@Override
	public int findDivisonWiseDeath(@Valid HospitalZone hospitalZone) {
		return 0;
	}

	@Override
	public int findAgeWiseDeath(@Positive int age) {
		return 0;
	}

	@Override
	public int findGenderWiseDeath(@Pattern(regexp = "^Male?$|^Female?$") String gender) {
		return 0;
	}

}
