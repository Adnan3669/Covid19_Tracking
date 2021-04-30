package com.covid19.model;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Table(name = "Hospital")
public class Hospital implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hospital_id")
	private int hospitalId;

	@Column(name = "hospital_name", length = 50, nullable = false)
	private String hospitalName;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_id")
	private HospitalZone hospitalZone;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private HospitalType hospitalType;

	@Column(name = "general_bed")
	private int hospitalGeneralBed;

	@Column(name = "icu_bed")
	private int hospitalIcuBed;

	@ManyToMany(mappedBy = "hospitals",fetch = FetchType.LAZY)
	private List<Admin> admins;

	@OneToMany(mappedBy = "hospital",fetch = FetchType.LAZY)
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

	public HospitalZone getHospitalZone() {
		return hospitalZone;
	}

	public void setHospitalZone(HospitalZone hospitalZone) {
		this.hospitalZone = hospitalZone;
	}

	public HospitalType getHospitalType() {
		return hospitalType;
	}

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

	public List<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

}
