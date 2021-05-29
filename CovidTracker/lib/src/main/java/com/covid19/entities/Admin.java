package com.covid19.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Scope("prototype")
@Table(name = "Admin")
@Controller
public class Admin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	  * * adminId which is primary key
	 */	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private int adminId;

	/*
	  * * adminFirstName which is String and related to column admin_lname
	 */	
	@Size(min = 3, max = 20)
	@NotBlank(message = "Admin First Name can't be Blank ")
	@Column(name = "admin_fname", length = 20, nullable = false)
	private String adminFirstName;


	/*
	 * * adminLastName which is String and related to column admin_lname
	 */	 
	@Size(min = 3, max = 20)
	@NotBlank(message = "Admin Last Name can't be Blank ")
	@Column(name = "admin_lname", length = 20, nullable = false)
	private String adminLastName;

	/*
	 * Admin Password which is String and related to column admin_pass
	 */
	@NotBlank
	//@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message = "Minimum eight characters, at least one letter and one number")
	@Column(name = "admin_pass", nullable = false)
	private String adminPassword;
	/*
	 * Admin UserName which is a String
	 */
	@NotBlank
	@Column(name = "admin_username", length = 20, nullable = false,unique =true )
	private String adminUserName;

	/*
	 * ManyToMany Relationship with Hospital related using mapping table
	 */	@Valid
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Admin_Hospital", joinColumns = { @JoinColumn(name = "admin_id") }, inverseJoinColumns = {
			@JoinColumn(name = "hospital_id") })
	private List<Hospital> hospitals;

	
	/*
	 * Getters and Setters
	 */	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}
	@JsonIgnore
	public String getAdminPassword() {
		return adminPassword;
	}
	@JsonSetter
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	@JsonIgnore
	public String getAdminUserName() {
		return adminUserName;
	}
	@JsonSetter
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	@JsonGetter
	public List<Hospital> getHospitals() {
		return hospitals;
	}
	@JsonIgnore
	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

}