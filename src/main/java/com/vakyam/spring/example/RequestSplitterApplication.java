package com.vakyam.spring.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan("com.vakyam.spring.requestsplitter")
public class RequestSplitterApplication {

	public static void main(String[] args) {

		SpringApplication.run(RequestSplitterApplication.class, args);
	}

}
