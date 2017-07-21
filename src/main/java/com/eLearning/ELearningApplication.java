package com.eLearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan("com.eLearning")
@SpringBootApplication
public class ELearningApplication extends SpringBootServletInitializer {

	/*This is the entry point of the application.*/
	
	public static void main(String[] args) {
		SpringApplication.run(ELearningApplication.class, args);
	}
}
