package com.covid19.repository;

import javax.validation.constraints.Positive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select admin from Admin admin where admin.adminId=:adminId")
	Admin findByAdminId(@Param("adminId") int adminId);
	
	@Query("Select l From Admin l Where l.adminUserName=:username And l.adminPassword=:password")
	Admin findAdminCredentials(@Param("username") String username, @Param("password")String password);
	
}
