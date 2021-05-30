package com.covid19.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Scope("prototype") // Create Bean Everytime
@Component
@Table(name = "Hospital")
public class Hospital implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id // Specifies That hospitalId is primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // Incrementing the hospitalId automatically
	@Column(name = "hospital_id")
	private int hospitalId;

	@NotBlank // Specifies that the hospitalName Should not be blank
	@Pattern(regexp = "[a-zA-Z\\s]*$", message = "Hospital name should be Alphabhetic only")
	@Size(min = 3, max = 50, message = "Hospital name is not in range")
	@Column(name = "hospital_name", length = 50)
	private String hospitalName;

	
	/*
	 * Giving Relationship Between Hospital and hospitalZone as many to one show
	 * that many hospital can have one zone
	 */
	@JsonIgnore
	@Valid
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_id") // Specifying zoneId as Foreign key of Hospital
	private HospitalZone hospitalZone;

	@Valid
	/*
	 * Giving Relationship Between Hospital and hospitalType as many to one show
	 * that many hospital can have one Type
	 */
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id") // Specifying typeId as Foreign key of Hospital
	private HospitalType hospitalType;

	@PositiveOrZero(message = "Entered  General Bed Count is not in Range")
	@Column(name = "general_bed")
	private int hospitalGeneralBed;

	@PositiveOrZero(message = "Entered  Icu Bed Count is not in Range")
	@Column(name = "icu_bed")
	private int hospitalIcuBed;

//	Giving ManyToMany Relation Between Admin and Hospital so that Many hospital have many admins
	@ManyToMany(mappedBy = "hospitals", fetch = FetchType.LAZY)
	private List<Admin> admins;

	@Valid
	// Giving OneToMany Relation Between Hospital and Patients so that One hospital
	// have many Patients
	
	@OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Patient> patients;
	
	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@JsonGetter
	public HospitalZone getHospitalZone() {
		return hospitalZone;
	}

	@JsonIgnore
	public void setHospitalZone(HospitalZone hospitalZone) {
		this.hospitalZone = hospitalZone;
	}

	@JsonGetter
	public HospitalType getHospitalType() {
		return hospitalType;
	}

	@JsonIgnore
	public void setHospitalType(HospitalType hospitalType) {
		this.hospitalType = hospitalType;
	}

	public int getHospitalGeneralBed() {
		return hospitalGeneralBed;
	}

	public void setHospitalGeneralBed(int hospitalGeneralBed) {
		this.hospitalGeneralBed = hospitalGeneralBed;
	}

	public int getHospitalICUBed() {
		return hospitalIcuBed;
	}

	public void setHospitalICUBed(int hospitalICUBed) {
		this.hospitalIcuBed = hospitalICUBed;
	}

	@JsonIgnore
	public List<Admin> getAdmins() {
		return admins;
	}

	@JsonIgnore
	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	@JsonGetter
	public List<Patient> getPatients() {
		return patients;
	}

	@JsonIgnore
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}

