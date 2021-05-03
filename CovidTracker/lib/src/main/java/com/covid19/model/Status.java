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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
/*@Entity It is used to represents table in relational database
 *@Scope It indicates the name of a scope to use for instances of annotated type
 *@Component It is used to denote a class as component
 *@Table It is used override the name of table
 * */
@Entity
@Scope("prototype")
@Component
@Table(name = "Status")
public class Status {
	/* 
	 * Here @Id annotation specifies primary key of entity
	 * @GeneratedValue provides for the specification of generation strategies
	 * @DateTimeFormat is used to declare a field should be formatted as a date or time
	 * @Column is used to specify the mapped column for a persistent field
	 * @NotNull means a method should not return null 
	 * @Valid It is used to validate the user's input
	 * @JoinColumn is used to specify a column for joining an entity association 
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "status_id")
	private int statusId;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "confirm_date")
	private LocalDate confirmDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "isolation_date")
	private LocalDate isolationDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "recovered_date")
	private LocalDate recoveredDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "death_date")
	private LocalDate deathDate;

	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	/*
	 * we have declared here getter and setter 
	 * getter method will returns value
	 * setter method will sets the value 
	 * */

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public LocalDate getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(LocalDate confirmDate) {
		this.confirmDate = confirmDate;
	}

	public LocalDate getIsolationDate() {
		return isolationDate;
	}

	public void setIsolationDate(LocalDate isolationDate) {
		this.isolationDate = isolationDate;
	}

	public LocalDate getRecoveredDate() {
		return recoveredDate;
	}

	public void setRecoveredDate(LocalDate recoveredDate) {
		this.recoveredDate = recoveredDate;
	}

	public LocalDate getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(LocalDate deathDate) {
		this.deathDate = deathDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	
	

}