package com.covid19.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Table(name = "Result")
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "result_id")
	private int resultId;

	@Column(name = "result_val", nullable = false)
	private String resultVal;
	
	@OneToMany(mappedBy = "result")
	private List<CovidTest> covidTests;

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public String getResultVal() {
		return resultVal;
	}

	public void setResultVal(String resultVal) {
		this.resultVal = resultVal;
	}

	public List<CovidTest> getTests() {
		return covidTests;
	}

	public void setTests(List<CovidTest> covidTests) {
		this.covidTests = covidTests;
	}

	public Result(String resultVal, List<CovidTest> covidTests) {
		super();
		this.resultVal = resultVal;
		this.covidTests = covidTests;
	}

	public Result() {
	}

}
