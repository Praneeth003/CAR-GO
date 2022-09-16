package com.cargobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class CargoApp extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CargoApp.class);
    }


	public static void main(String[] args) {
		System.out.println("\n CargoApp start");
		SpringApplication.run(CargoApp.class, args);
		System.out.println("\n CargoApp end");
	}

}
