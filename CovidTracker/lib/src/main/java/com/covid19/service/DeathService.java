package com.covid19.service;

import com.covid19.exceptions.IllegalAgeException;
import com.covid19.exceptions.NoSuchGenderException;
import com.covid19.exceptions.NoSuchMonthException;
import com.covid19.exceptions.NoSuchZoneException;

public interface DeathService {
	public int findDeathByMonth(String month) throws NoSuchMonthException;
	public int findDeathAllZone();
	public int findDeathByZone(String zone) throws NoSuchZoneException;
	public int findDeathByAge(int age) throws IllegalAgeException;
	public int findDeathByGender(int gender) throws NoSuchGenderException;
}
