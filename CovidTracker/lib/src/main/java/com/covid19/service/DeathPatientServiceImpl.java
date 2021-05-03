package com.covid19.service;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;

import com.covid19.model.Hospital;
import com.covid19.model.HospitalZone;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;

public class DeathPatientServiceImpl implements DeathPatientService {

	@Autowired
	StatusRepository statusRepository;
	@Autowired
	HospitalZoneRepositary hospitalZoneRepositary;

	@Override
	public int findMonthWiseDeath(@Min(1) @Max(12) int month) {
		LocalDate firstDay = LocalDate.of(LocalDate.now().getYear(), month, 1);
		LocalDate lastDay = LocalDate.of(LocalDate.now().getYear(), month, 30);
		statusRepository.findTotalDeathOfMonth(firstDay, lastDay);
		return month;
	}

	@Override
	public int findDivisonWiseDeath(@NotNull String hospitalZone) {

		HospitalZone zone = hospitalZoneRepositary.findHospitalZoneByName(hospitalZone);
		return statusRepository.findTotalDeathByZone(zone.getZoneId());

	}

	@Override
	public int findAgeWiseDeath(@Positive int age) {
		return statusRepository.findTotalDeathByAge(age);
	}

	@Override
	public int findGenderWiseDeath(@Pattern(regexp = "^Male?$|^Female?$") String gender) {
		return statusRepository.findTotalDeathByGender(gender);
	}

}
