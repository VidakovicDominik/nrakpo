package com.vidakovic.nrakpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NrakpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NrakpoApplication.class, args);
	}

}
