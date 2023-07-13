package com.alexandru.SpringBootStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SpringBootStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStoreApplication.class, args);
	}

}
