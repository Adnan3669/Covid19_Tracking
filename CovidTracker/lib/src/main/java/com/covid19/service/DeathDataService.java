package com.covid19.service;

import java.util.Date;

import com.covid19.model.Zone;

public interface DeathDataService {
	public int findMonthWiseDeath(Date date);

	public int findDivisonByDeath(Zone zone);

	public int findAgeByDeath(int age);

	public int findGenderByDeath(String gender);
}