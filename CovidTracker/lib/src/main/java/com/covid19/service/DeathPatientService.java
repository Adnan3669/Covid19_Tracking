package com.covid19.service;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.covid19.model.HospitalZone;

public interface DeathPatientService {
	public int findMonthWiseDeath(@Min(1) @Max(12) int month);

	public int findDivisonWiseDeath(@Valid HospitalZone hospitalZone);

	public int findAgeWiseDeath(@Positive int age);

	public int findGenderWiseDeath(@Pattern(regexp = "^Male?$|^Female?$") String gender);
}