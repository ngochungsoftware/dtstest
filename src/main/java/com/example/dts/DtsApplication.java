package com.example.dts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtsApplication.class, args);
	}

	@Bean
	public CommandLineRunner printContextPath(@Value("${server.servlet.context-path:}") String contextPath) {
		return args -> {
			System.out.println(">>> CONTEXT PATH: " + contextPath);
		};
	}

}
