package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid19.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
