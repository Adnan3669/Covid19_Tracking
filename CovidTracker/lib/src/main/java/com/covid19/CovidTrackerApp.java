package com.covid19;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CovidTrackerApp {
	@Bean("list")
	public List<String>  getListOfString()
	{
		return new ArrayList<>();
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(CovidTrackerApp.class, args);
	}

}
