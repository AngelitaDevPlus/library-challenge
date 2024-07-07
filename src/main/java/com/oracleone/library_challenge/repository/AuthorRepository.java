package com.oracleone.library_challenge.repository;

import com.oracleone.library_challenge.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameContainsIgnoreCase(String name);

    Optional<Author> findByNameIgnoreCase(String name);
    @Query("SELECT a FROM Author a WHERE a.birthYear < :year AND a.deathYear > :year")
    Optional<List<Author>> findAuthorsAliveInAGivenYear(int year);
}
