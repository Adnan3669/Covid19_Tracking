package com.covid19.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Table(name = "Test")
public class Test {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "test_id")
	private int testId;
	
	@Column(name = "test_result_date",nullable = false)
	private LocalDate testDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="patient_id" )
	private Patient patient;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="result_id" )
	private Result result;

	public LocalDate getTestDate() {
		return testDate;
	}

	public void setTestDate(LocalDate testDate) {
		this.testDate = testDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	public Test() {
		// TODO Auto-generated constructor stub
	}

	public Test(LocalDate testDate, Patient patient, Result result) {
		super();
		this.testDate = testDate;
		this.patient = patient;
		this.result = result;
	}
	
}