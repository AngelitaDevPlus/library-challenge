package com.oracleone.library_challenge.repository;

import com.oracleone.library_challenge.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
