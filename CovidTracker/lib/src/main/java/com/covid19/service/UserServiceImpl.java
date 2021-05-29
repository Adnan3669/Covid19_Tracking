package com.covid19.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalZone;
import com.covid19.entities.Patient;
import com.covid19.entities.Record;
import com.covid19.entities.Status;
import com.covid19.repository.HospitalRepository;
import com.covid19.repository.HospitalZoneRepositary;
import com.covid19.repository.StatusRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	List<String> list;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private HospitalZoneRepositary hospitalZoneRepositary;
	@Autowired
	Record record;

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

	@Override
	public Record findTotalDataBasedOnZoneAndDate(@Positive int zoneId,
			@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		Integer death = 0;
		Integer active = 0;
		Integer recovered = 0;
		Integer confirm = 0;
		HospitalZone zone = hospitalZoneRepositary.findHospitalZoneById(zoneId);
		for (Hospital hospital : zone.getHospitals()) {
			confirm += statusRepository.findTotalCasesinParticularDate(date, hospital.getHospitalId());
			death += statusRepository.findTotalDeathinParticularDate(date, hospital.getHospitalId());
			recovered += statusRepository.findTotalRecoveredinParticularDate(date, hospital.getHospitalId());
			active += statusRepository.findTotalActiveinParticularDate(date, hospital.getHospitalId());
		}

		record.setActive(active);
		record.setConfirmed(confirm);
		record.setDeath(death);
		record.setRecovered(recovered);
		return record;

	}

	@Autowired
	HospitalRepository hospitalRepository;
	@Override
	public Map<String, Record> findTotalDataBasedOnZone() {
		Map<String, Record> totalRecords = new HashMap();
		List<HospitalZone> zones = hospitalZoneRepositary.findAll();
		for (HospitalZone zone : zones) {
			Record record=new Record();
			Integer death = 0;
			Integer active = 0;
			Integer recovered = 0;
			Integer confirm = 0;
//			System.out.println(zone.getZoneId());
			
			for (Hospital hospital : hospitalRepository.findAll()) {
				System.out.println("hospital"+ hospital.getHospitalName());
				if(hospital.getHospitalZone().getZoneId()==zone.getZoneId())
				{
					System.out.println("zone "+zone.getZoneId());
				for (Patient patient : hospital.getPatients()) {
					for(Status status: patient.getStatus())
					{
						if(status.getConfirmDate()!=null)
							confirm++;
						if(status.getIsolationDate()!=null)
						{
							if(status.getRecoveredDate()==null&&status.getDeathDate()==null)
							active++;
						}
						if(status.getRecoveredDate()!=null)
							recovered++;
						if(status.getDeathDate()!=null)
							death++;
					}
				}
				}
				
			}
			
			record.setActive(active);
			record.setConfirmed(confirm);
			record.setDeath(death);
			record.setRecovered(recovered);
			totalRecords.put(zone.getZoneName(), record);
			
			
		}

		return totalRecords;

	}

	@Override
	public Map<Integer, Record> findTotalDataBasedOnMonth(int startMonth, int endMonth) {
		Integer death = 0;
		Integer active = 0;
		Integer recovered = 0;
		Integer confirm = 0;
		Map<Integer, Record> monthRecords = new HashMap();
		for (int i = startMonth; i < endMonth + 1; i++) {
			Record record = new Record();
			LocalDate firstDay = LocalDate.of(LocalDate.now().getYear(), i, 1);
			LocalDate lastDay = LocalDate.of(LocalDate.now().getYear(), i, firstDay.lengthOfMonth());
			confirm += statusRepository.findTotalCasesinMonth(firstDay, lastDay);
			death += statusRepository.findTotalDeathinMonth(firstDay, lastDay);
			recovered += statusRepository.findTotalRecoveredinMonth(firstDay, lastDay);
			active += statusRepository.findTotalActiveinMonth(firstDay, lastDay);
			record.setActive(active);
			record.setConfirmed(confirm);
			record.setDeath(death);
			record.setRecovered(recovered);
			monthRecords.put(i, record);
		}

		return monthRecords;

	}

}