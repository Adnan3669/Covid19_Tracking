package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.covid19.model.Admin;
import com.covid19.model.Test;

public interface TestRepository extends JpaRepository<Test, Integer> {

}
