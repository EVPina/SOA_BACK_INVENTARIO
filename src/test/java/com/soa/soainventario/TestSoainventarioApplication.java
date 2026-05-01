package com.soa.soainventario;

import org.springframework.boot.SpringApplication;

public class TestSoainventarioApplication {

	public static void main(String[] args) {
		SpringApplication.from(SoainventarioApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
