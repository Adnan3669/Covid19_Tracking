package com.covid19.service;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public interface DeathPatientService {
	public int findMonthWiseDeath(@Min(1) @Max(12) int month);

	public int findDivisonWiseDeath(@NotNull String hospitalZone);

	public int findAgeWiseDeath(@Positive int age);

	public int findGenderWiseDeath(@Pattern(regexp = "^Male?$|^Female?$") String gender);
}