package com.pokebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.pokebackend.adapter.out.persistence")
@ComponentScan(basePackages = "com.pokebackend")
@EntityScan(basePackages = "com.pokebackend.domain")
public class PokebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokebackendApplication.class, args);
	}
}
