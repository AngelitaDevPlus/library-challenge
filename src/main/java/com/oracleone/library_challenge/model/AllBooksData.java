package com.oracleone.library_challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AllBooksData(
        @JsonAlias("results") List<BookData> apiResults
) {
}
