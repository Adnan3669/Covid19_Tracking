package com.covid19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sun.tools.javac.Main;
// new app
@SpringBootApplication
public class CovidTrackerApp {
	
	public static void main(String[] args) {
		
//		SpringApplication.run(Main.class, args);
		SpringApplication.run(CovidTrackerApp.class, args);
	}

}
