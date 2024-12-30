package com.lfch.challenge_literatura_alura;

import com.lfch.challenge_literatura_alura.model.Libro;
import com.lfch.challenge_literatura_alura.principal.Principal;
import com.lfch.challenge_literatura_alura.repository.AutorRepository;
import com.lfch.challenge_literatura_alura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ChallengeLiteraturaAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AutorRepository repositoryAutor;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository,repositoryAutor);
		principal.muestraElMenu();

	}
}