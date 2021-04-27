package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.covid19.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
