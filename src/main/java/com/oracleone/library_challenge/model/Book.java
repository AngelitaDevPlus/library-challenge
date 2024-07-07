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
    private Languages language;
    private Double downloadCount;

    public Book() {
    }

    public Book(BookData bookData) {
        this.title = bookData.title();
        //this.author =
        this.language = Languages.fromString(bookData.languagesList().get(0));
        this.downloadCount = bookData.downloadCount();
    }

    public Author fromListToAuthor(BookData bookData){
        return new Author(bookData.authorDataList().get(0).name(),
                bookData.authorDataList().get(0).birthYear(),
                bookData.authorDataList().get(0).deathYear());
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

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
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
                "Language: " + language + "\n" +
                "Download Count: " + downloadCount + "\n" +
                 author.toString() + "\n" +
                "-----------------------------------------------------------\n";
    }
}
