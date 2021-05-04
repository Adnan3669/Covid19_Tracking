package com.covid19.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Scope("prototype")
@Component
@Table(name = "HospitalType")
public class HospitalType implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_id")
	private int typeId;
	@NotBlank
	@Pattern(regexp = "[A-Za-z]+$",message = "Invalid Characters entered for TypeName")
	@Column(name="type_name",length = 20,nullable = false,unique = true)
	private String typeName;
	
	@Valid
	@OneToMany(mappedBy = "hospitalType",fetch = FetchType.LAZY)
	private List<Hospital> hospitals;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@JsonIgnore
	public List<Hospital> getHospitals() {
		return hospitals;
	}
	@JsonIgnore
	public void setHospitals(List<Hospital> hospitals) {
		this.hospitals = hospitals;
	}
	
}