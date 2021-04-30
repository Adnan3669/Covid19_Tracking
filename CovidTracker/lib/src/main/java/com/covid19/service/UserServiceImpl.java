package com.covid19.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid19.repository.StatusRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private StatusRepository statusRepository;

	@Override
	public int findTotalCases() {

		return statusRepository.findTotalCases();
	}

	@Override
	public int findTotalCasesIn24Hrs(LocalDate currentDate, LocalDate passedDate) {
		return statusRepository.findTotalCasesIn24Hrs(currentDate, passedDate);
	}

	@Override
	public int findTotalLabTest() {
		return statusRepository.findTotalLabTest();
	}

	@Override
	public int findTotalConfirmedCases() {
		return statusRepository.findTotalConfirmedCases();
	}

	@Override
	public int findTotalRecoveredCases() {
		return statusRepository.findTotalRecoverdCases();
	}

	@Override
	public int findTotalPatientInIsolation() {
		return statusRepository.findTotalPatientInIsolation();
	}

	@Override
	public int findTotalDeath() {
		return statusRepository.findTotalDeath();
	}

}