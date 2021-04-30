package com.covid19.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

	
	@Query("Select count(a) From Status a")
	public int findTotalCases();

	
	/*@Query(SELECT * FROM Status AS "Users"
			WHERE "Users"."created_date" >= NOW() - INTERVAL '24 HOURS'
			ORDER BY "Users"."created_date" DESC)*/
	@Query("Select count (a) From Status a Where a.confirmDate between :currentDate and :passedDate")
	public int findTotalCasesIn24Hrs(@Param("currentDate") LocalDate currentDate, @Param("passedDate") LocalDate passedDate);
	
	@Query("Select count(a) From Test a")
	public int findTotalLabTest();
	
	@Query("Select count(a) From Status a Where a.confirmDate is not null")
	public int findTotalConfirmedCases();

	@Query("Select count(a) From Status a Where a.recoveredDate is not null")
	public int findTotalRecoverdCases();
	
	@Query("Select count(a) From Status a Where a.isolationDate is not null and(a.recoveredDate is null and a.deathDate is null) ")
	public int findTotalPatientInIsolation();
	
	@Query("Select count(a) From Status a Where a.deathDate is not null")
	public int findTotalDeath();


}