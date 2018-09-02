package com.vakyam.spring.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@SpringBootApplication
@ComponentScan("com.vakyam.spring.requestsplitter")
public class RequestSplitterApplication {

	public static void main(String[] args) {

		SpringApplication.run(RequestSplitterApplication.class, args);
	}

}
