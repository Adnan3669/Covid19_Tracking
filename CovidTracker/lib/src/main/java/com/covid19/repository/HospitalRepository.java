package com.covid19.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
	@Query("Select s from Hospital s Where s.hospitalType.typeName=:typeName")
	public List<Hospital> findHospitalByType(@Param("typeName") String type);

	@Query("Select s from Hospital s Where s.hospitalZone.zoneName=:zoneName")
	public List<Hospital> findHospitalByZone(@Param("zoneName") String zone);

	@Query("Select s from Hospital s where s.hospitalGeneralBed!=0 or s.hospitalIcuBed!=0 ")
	public List<Hospital> findHospitalByFreeBeds();

	@Query("Select hospital from Hospital hospital where hospital.hospitalId=:hospitalId" )
	public Hospital findByHospitalId(@Param("hospitalId") int hospitalId);
}