package com.oracleone.library_challenge;

import com.oracleone.library_challenge.main.MainClass;
import com.oracleone.library_challenge.repository.AuthorRepository;
import com.oracleone.library_challenge.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryChallengeApplication implements CommandLineRunner {
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository bookRepository;
	public static void main(String[] args) {
		SpringApplication.run(LibraryChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MainClass mainClass = new MainClass(authorRepository, bookRepository);
		mainClass.showMenu();
	}
}
