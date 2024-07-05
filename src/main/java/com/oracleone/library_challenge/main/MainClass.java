package com.oracleone.library_challenge.main;

import com.oracleone.library_challenge.model.Book;
import com.oracleone.library_challenge.model.BookData;
import com.oracleone.library_challenge.repository.AuthorRepository;
import com.oracleone.library_challenge.repository.BookRepository;
import com.oracleone.library_challenge.service.APIConnection;
import com.oracleone.library_challenge.service.DataConversion;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainClass {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private Scanner keyboard = new Scanner(System.in);
    private APIConnection apiConnection = new APIConnection();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private DataConversion dataConversion = new DataConversion();
    public MainClass(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void showMenu() {
        var menu = """
                    1- Register book
                    2- Show registered books
                    3- Search books by language
                    4- Show registered authors
                    5- Show authors alive on a given year
                    6- Search book by Title 
                                        
                    0- Exit
                    
                    """;
        var option = -1;
        while (option != 0) {
            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    registerBook();
                    break;
                case 2:
                    showRegisteredBooks();
                    break;
                case 3:
                    searchBooksByLanguage();
                    break;
                case 4:
                    showRegisteredAuthors();
                    break;
                case 5:
                    showAuthorsAliveOnAGivenYear();
                    break;
                case 6:
                    searchBookByTitle();
                case 0:
                    System.out.println("Closing app...");
                    break;
                default:
                    System.out.println("Invalid option :(");
            }
        }
    }

    private BookData getBookData() {
        System.out.println("Enter the title of the book to register: ");
        var bookTitle = keyboard.nextLine();
        var json = apiConnection.dataInput(URL_BASE
                + URLEncoder.encode(bookTitle, StandardCharsets.UTF_8));
        System.out.println(json);
        BookData bookData = dataConversion.getData(json, BookData.class);
        return bookData;
    }

    private void registerBook() {
        BookData bookData = getBookData();
        Book book = new Book(bookData);
        bookRepository.save(book);
        System.out.println(bookData);
    }

    private void showRegisteredBooks() {
    }

    private void searchBooksByLanguage() {
    }

    private void showRegisteredAuthors() {
    }

    private void showAuthorsAliveOnAGivenYear() {
    }

    private void searchBookByTitle() {
    }


}
