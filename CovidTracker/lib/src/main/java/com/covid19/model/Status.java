package com.covid19.model;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Table(name = "Status")
public class Status {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "status_id")
	private int statusId;

	@Column(name = "confirm_date")
	private LocalDate confirmDate;

	@Column(name = "isolation_date")
	private LocalDate isolationDate;

	@Column(name = "recovered_date")
	private LocalDate recoveredDate;

	@Column(name = "death_date")
	private LocalDate deathDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_id")
	private Patient patient;

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