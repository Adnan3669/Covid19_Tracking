package com.covid19.service;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.covid19.entities.HospitalZone;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;

@Service
@Validated
public class DeathPatientServiceImpl implements DeathPatientService {

	@Autowired
	StatusRepository statusRepository;
	@Autowired
	HospitalZoneRepositary hospitalZoneRepositary;

	@Override
	public int findMonthWiseDeath(@Min(1) @Max(12) int month) {
		LocalDate firstDay = LocalDate.of(LocalDate.now().getYear(), month, 1);
		LocalDate lastDay = LocalDate.of(LocalDate.now().getYear(), month, firstDay.lengthOfMonth());
		return statusRepository.findTotalDeathOfMonth(firstDay, lastDay);
		
	}

	@Override
	public int findDivisonWiseDeath(@NotNull String hospitalZone) {

		HospitalZone zone = hospitalZoneRepositary.findHospitalZoneByName(hospitalZone);
		return statusRepository.findTotalDeathByZone(zone.getZoneId());

	}

	@Override
	public int findAgeWiseDeath(@Positive int fromAge,@Positive int toAge) {
		return statusRepository.findTotalDeathByAge(fromAge,toAge);
	}

	@Override
	public int findGenderWiseDeath(@Pattern(regexp = "^Male?$|^Female?$") String gender) {
		return statusRepository.findTotalDeathByGender(gender);
	}

}
