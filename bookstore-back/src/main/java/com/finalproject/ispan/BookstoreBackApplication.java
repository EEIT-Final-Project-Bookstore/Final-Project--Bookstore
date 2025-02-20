package com.finalproject.ispan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.finalproject.ispan")
public class BookstoreBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreBackApplication.class, args);
	}

}
