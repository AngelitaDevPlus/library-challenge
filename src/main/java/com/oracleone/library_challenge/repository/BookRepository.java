package com.oracleone.library_challenge.repository;

import com.oracleone.library_challenge.model.Book;
import com.oracleone.library_challenge.model.BookData;
import com.oracleone.library_challenge.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleIgnoreCase(String title);

    List<Book> findByLanguage(Languages language);

    List<Book> findByTitleContainsIgnoreCase(String title);
}
