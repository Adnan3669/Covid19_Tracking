package com.covid19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.covid19.model.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select admin from Admin admin where admin.adminId=:adminId")
	Admin findByAdminId(@Param("adminId") int adminId);

}
