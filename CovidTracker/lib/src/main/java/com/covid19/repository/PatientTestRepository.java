package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid19.entities.Admin;
import com.covid19.entities.CovidTest;

@Repository
public interface PatientTestRepository extends JpaRepository<CovidTest, Integer> {

}
