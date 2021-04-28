package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.covid19.model.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByAdminId(int adminId);

}
