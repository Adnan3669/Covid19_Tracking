  
package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.entities.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	@Query("Select patient from Patient patient where patient.patientId=:patientId ")
	Patient findByPatientId(@Param("patientId") int patientId);
}