package com.simona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimonaApplication.class, args);
	}
}
