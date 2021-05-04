package com.covid19.model;

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
@Table(name = "HospitalZone")
@Component
@Scope("prototype")
public class HospitalZone implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "zone_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int zoneId;
	
	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9]+$", message = "Invalid Characters entered for Hospital Zone")
	@Column(name = "zone_name", length = 20, nullable = false,unique = true)
	private String zoneName;

	@Valid
	@OneToMany(mappedBy = "hospitalZone",fetch = FetchType.LAZY)
	private List<Hospital> hospitals;

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
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