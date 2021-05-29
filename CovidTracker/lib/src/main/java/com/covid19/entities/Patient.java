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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.checkerframework.checker.index.qual.Positive;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Scope("prototype")
@Component
@Table(name = "Patient")
public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO) // will generate values of primary key in auto type.
	@Id // will make patient id as primary key.
	@Column(name = "patient_id")
	private int patientId;

	@NotBlank
	@Pattern(regexp = "[A-Za-z]*", message = "Patient First Name is not appropriate") // used to achieve regular
																						// expression validation.
	@Column(name = "patient_fname", length = 20) // setup length of String of "patient_fname" column.
	private String patientFirstName;

	@Pattern(regexp = "[A-Za-z']+$", message = "Patient Last Name is not appropriate") // used to achieve regular
																						// expression validation.
	@Column(name = "patient_lname", length = 20) // setup length of String of "patient_lname" column.
	private String patientLastName;

	@Max(value = 9999999999L, message = "Inappropriate mobile number entered!") // maximum value for column.
	@Min(value = 1111111111, message = "Inappropriate mobile number entered!") // minimum value for column.
	@Column(name = "patient_mobileno", length = 10)
	private long patientMobileNo;

	@Positive // value should be positive.
	@Column(name = "patient_age", length = 3)
	private int patientAge;

	@NotBlank // this field cannot be blank.
	@Pattern(regexp = "^Male?$|^Female?$", message = "Input can be Male or Female ") // will achieve regular expression
																						// validation for field.
	@Column(name = "patient_gender")
	private String patientGender;

	@Valid // allows validate object.
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Many to one association with hospital Table.
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Valid // allows validate object.
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY) // one to many association with covidTest Table.
	private List<CovidTest> covidTest;

	@Valid // allows validate object,
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY) // one to many association with status Table.
	private List<Status> status;

	public Patient() {

	}

	/*
	 * Getters and Setters.
	 */
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
	@JsonIgnore
	public Hospital getHospital() {
		return hospital;
	}
	@JsonIgnore
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@JsonGetter
	public List<CovidTest> getTest() {
		return covidTest;
	}

	@JsonIgnore
	public void setTest(List<CovidTest> covidTest) {
		this.covidTest = covidTest;
	}

	@JsonGetter
	public List<Status> getStatus() {
		return status;
	}

	@JsonIgnore
	public void setStatus(List<Status> status) {
		this.status = status;
	}

}