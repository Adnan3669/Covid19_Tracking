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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Table(name = "Patient")
public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "patient_id")
	private int patientId;
	
	@NotBlank
	@Pattern(regexp = "[A-Za-z]*",message ="Patient First Name is not appropriate" )
	@Column(name = "patient_fname", length = 20)
	private String patientFirstName;

	@Pattern(regexp = "[A-Za-z'.\\s]+$",message = "Patient First Name is not appropriate")
	@Column(name = "patient_lname", length = 20)
	private String patientLastName;

	@Pattern(regexp = "^[0-9]{10}$",message = "Inappropriate mobile number entered!")
	@Column(name = "patient_mobileno", length = 10)
	private long patientMobileNo;

	
	@Positive
	@Column(name = "patient_age", length = 3)
	private int patientAge;

	@NotBlank
	@Pattern(regexp = "^Male?$|^Female?$",message = "Input can be Male or Female ")
	@Column(name = "patient_gender")
	private String patientGender;

	@Valid
	@ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;
	
	@Valid
	@OneToMany(mappedBy = "patient",  fetch = FetchType.LAZY)
	private List<CovidTest> covidTest;
	
	@Valid
	@OneToMany(mappedBy = "patient",  fetch = FetchType.LAZY)
	private List<Status> status;


	public Patient() {
		
	}
	
	public Patient(String patientFirstName, String patientLastName, long patientMobileNo, int patientAge,
			String patientGender, Hospital hospital, List<CovidTest> covidTest, List<Status> status) {
		super();
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
		this.patientMobileNo = patientMobileNo;
		this.patientAge = patientAge;
		this.patientGender = patientGender;
		this.hospital = hospital;
		this.covidTest = covidTest;
		this.status = status;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public long getPatientMobileNo() {
		return patientMobileNo;
	}

	public void setPatientMobileNo(long patientMobileNo) {
		this.patientMobileNo = patientMobileNo;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public List<CovidTest> getTest() {
		return covidTest;
	}

	public void setTest(List<CovidTest> covidTest) {
		this.covidTest = covidTest;
	}

	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}


}