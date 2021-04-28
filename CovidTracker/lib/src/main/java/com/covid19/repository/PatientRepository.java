package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid19.model.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
