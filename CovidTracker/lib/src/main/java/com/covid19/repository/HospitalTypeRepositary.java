package com.covid19.repository;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.model.HospitalType;

@Repository
public interface HospitalTypeRepositary extends JpaRepository<HospitalType, Integer> {
	@UniqueElements
	@Query("select type from HospitalType type where type.typeName=:typeName")
	public HospitalType findHospitalTypeByName(@Param("typeName") String hospitalTypeName);
	@UniqueElements
	@Query("select type from HospitalType type where type.typeId=:typeId")
	public HospitalType findHospitalTypeById(@Param("typeId")int typeId);
}