package com.covid19.entities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("prototype")
@Component
public class Record   {
	/**
	 * 
	 */
	Integer death;
	Integer active;
	Integer recovered;
	Integer confirmed;
	public Integer getDeath() {
		return death;
	}
	public void setDeath(Integer death) {
		this.death = death;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getRecovered() {
		return recovered;
	}
	public void setRecovered(Integer recovered) {
		this.recovered = recovered;
	}
	public Integer getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}
	
	
	

}