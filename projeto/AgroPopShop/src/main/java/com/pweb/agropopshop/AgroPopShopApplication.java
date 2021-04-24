package com.pweb.agropopshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.pweb.agropopshop.model")
public class AgroPopShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroPopShopApplication.class, args);
	}

}
