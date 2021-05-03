package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.model.HospitalZone;

@Repository
public interface HospitalZoneRepositary extends JpaRepository<HospitalZone, Integer> {
	@Query("select zone from HospitalZone zone where zone.zoneName=:zoneName")
	public HospitalZone findHospitalZoneByName(@Param("zoneName") String hospitalZoneName);
	@Query("select zone from HospitalZone zone where zone.zoneId=:zoneId")
	public HospitalZone findHospitalZoneById(@Param("zoneId")  int hospitalZoneId);
}