package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.covid19.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

}
