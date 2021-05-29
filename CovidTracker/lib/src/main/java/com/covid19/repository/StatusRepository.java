package com.covid19.repository;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.entities.HospitalZone;
import com.covid19.entities.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

	
	@Query("Select count(a) From Status a")
	public int findTotalCases();

	@Query("Select count(a) From Status a Where a.confirmDate = :currentDate or a.confirmDate =:passedDate")
	public int findTotalCasesIn24Hrs(@Param("currentDate") LocalDate currentDate, @Param("passedDate") LocalDate passedDate);
	
	@Query("Select count(a) From CovidTest a")
	public int findTotalLabTest();
	
	@Query("Select count(a) From Status a Where a.confirmDate is not null")
	public int findTotalConfirmedCases();

	@Query("Select count(a) From Status a Where a.recoveredDate is not null")
	public int findTotalRecoverdCases();
	
	@Query("Select count(a) From Status a Where a.isolationDate is not null and(a.recoveredDate is null and a.deathDate is null) ")
	public int findTotalPatientInIsolation();
	
	@Query("Select count(a) From Status a Where a.deathDate is not null")
	public int findTotalDeath();
	
	@Query("Select status From Status status Where status.statusId=:statusId")
	public Status findStatusById(@Param("statusId") int statusId);


	@Query("Select count(s) from Status s where s.patient.hospital.hospitalId=:hospitalId and s.confirmDate=:date")
	public Integer findTotalCasesinParticularDate(@Param("date") LocalDate date,@Param("hospitalId") int hospitalId);
	
	@Query("Select count(s) from Status s where s.patient.hospital.hospitalId=:hospitalId and s.isolationDate<=:date and (s.recoveredDate >:date or s.deathDate>:date or (s.deathDate is null and s.recoveredDate is null))")
	public Integer findTotalActiveinParticularDate(@Param("date") LocalDate date,@Param("hospitalId") int hospitalId);
	
	@Query("Select count(s) from Status s where s.patient.hospital.hospitalId=:hospitalId and s.recoveredDate=:date")
	public Integer findTotalRecoveredinParticularDate(@Param("date") LocalDate date,@Param("hospitalId") int hospitalId);
	@Query("Select count(s) from Status s where s.patient.hospital.hospitalId=:hospitalId and s.deathDate=:date")
	public Integer findTotalDeathinParticularDate(@Param("date") LocalDate date,@Param("hospitalId") int hospitalId);

	@Query("Select count(s) from Status s where  s.deathDate>=:firstDay and s.deathDate<=:lastDay ")
	public int findTotalDeathOfMonth(@Param("firstDay") LocalDate firstDay, @Param("lastDay") LocalDate lastDay);

	@Query("Select count(s) from Status s where s.patient.hospital.hospitalZone.zoneId=:zoneId and s.deathDate is not null")
	public int findTotalDeathByZone(@Param("zoneId") @NotNull int zoneId);
	
	@Query("Select count(s) from Status s where s.patient.patientAge=:age and s.deathDate is not null")
	public int findTotalDeathByAge(@Param("age") int age);

	@Query("Select count(s) from Status s where s.patient.patientGender=:gender and s.deathDate is not null")
	public int findTotalDeathByGender( @Param("gender") String gender);
	
	@Query("Select count(s) from Status s where  s.confirmDate>=:firstDay and s.confirmDate<=:lastDay ")
	public Integer findTotalCasesinMonth(@Param("firstDay") LocalDate firstDay, @Param("lastDay") LocalDate lastDay);

	@Query("Select count(s) from Status s where  s.deathDate>=:firstDay and s.deathDate<=:lastDay ")
	public Integer findTotalDeathinMonth(@Param("firstDay") LocalDate firstDay, @Param("lastDay") LocalDate lastDay);

	@Query("Select count(s) from Status s where  s.recoveredDate>=:firstDay and s.recoveredDate<=:lastDay ")
	public Integer findTotalRecoveredinMonth(@Param("firstDay") LocalDate firstDay, @Param("lastDay") LocalDate lastDay);
	
	@Query("Select count(s) from Status s where  s.isolationDate>=:firstDay and s.isolationDate<=:lastDay ")
	public Integer findTotalActiveinMonth(@Param("firstDay") LocalDate firstDay, @Param("lastDay") LocalDate lastDay);
	@Query("select status from Status status where status.patient.patientId=:patientId")
	public Status findStatusByPatientId(@Param("patientId")@Positive int patientId);

}