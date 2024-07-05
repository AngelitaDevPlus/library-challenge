package com.oracleone.library_challenge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne
    @JoinColumn(name = "authors_id")
    private Author author;
    @Enumerated(EnumType.STRING)
    private String language;
    private Double downloadCount;

    public Book() {
    }

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.author = new Author(bookData.authorDataList().get(0));
        this.language = String.valueOf(Languages.fromString(bookData.languagesList().get(0)));
        this.downloadCount = bookData.downloadCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "-----------------------------------------------------------\n" +
                "Book id: " + id + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author.getClass().getName() + "\n" +
                "Language: " + language + "\n" +
                "Download Count: " + downloadCount + "\n" +
                "-----------------------------------------------------------\n";
    }
}
