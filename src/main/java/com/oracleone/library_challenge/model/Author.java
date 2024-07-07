package com.oracleone.library_challenge.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> bookList;

    public Author() {
    }

    public Author(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public void setBookList(List<Book> bookList) {
        bookList.forEach(b -> b.setAuthor(this));
        this.bookList = bookList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    @Override
    public String toString() {
        if(!(bookList == null)) {
            return "-----------------------------------------------------------\n" +
                    "Author id: " + id + "\n" +
                    "Name: " + name + "\n" +
                    "Birth Year: " + birthYear + "\n" +
                    "Death Year: " + deathYear + "\n" +
                    "bookList: " + "\n" +
                    bookList.stream().map(Book::getTitle).toList() + "\n" +
                    "-----------------------------------------------------------\n";
        } else {
            return "-----------------------------------------------------------\n" +
                    "Author id: " + id + "\n" +
                    "Name: " + name + "\n" +
                    "Birth Year: " + birthYear + "\n" +
                    "Death Year: " + deathYear + "\n" +
                    "-----------------------------------------------------------\n";
        }
    }
}
