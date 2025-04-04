package com.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.projeto.repository")
public class Projeto1Application {
	public static void main(String[] args) {
		SpringApplication.run(Projeto1Application.class, args);
	}

}