package com.sedikev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sedikev") // Asegúrate de que escanea tu paquete
public class SedikevApplication {
	public static void main(String[] args) {
		SpringApplication.run(SedikevApplication.class, args);
	}
}
