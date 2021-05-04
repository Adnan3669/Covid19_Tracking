package com.covid19.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Table(name = "CovidTest")
public class CovidTest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "test_id")
	private int testId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "test_result_date",nullable = false)
	private LocalDate testDate;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="patient_id" )
	private Patient patient;
	
	@NotBlank
	@Pattern(regexp = "^Positive?$|^Negative?$",message = "Input can be Positive or Negative ")
	@Column(name ="result_val" )
	private String result;
	
	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

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


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	
}