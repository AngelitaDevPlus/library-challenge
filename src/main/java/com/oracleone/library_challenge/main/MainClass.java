package com.oracleone.library_challenge.main;

import com.oracleone.library_challenge.model.*;
import com.oracleone.library_challenge.repository.AuthorRepository;
import com.oracleone.library_challenge.repository.BookRepository;
import com.oracleone.library_challenge.service.APIConnection;
import com.oracleone.library_challenge.service.DataConversion;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class MainClass {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private Scanner keyboard = new Scanner(System.in);
    private APIConnection apiConnection = new APIConnection();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private DataConversion dataConversion = new DataConversion();
    private List<Book> booksList;
    private List<Author> authorList;

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
                    break;
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
        var allBooksData = dataConversion.getData(json, AllBooksData.class);
        Optional<BookData> bookData = allBooksData.apiResults().stream()
                .filter(l -> l.title().toUpperCase().contains(bookTitle.toUpperCase()))
                .findFirst();

        return bookData.orElse(null);
    }

    private void registerBook() {
        BookData bookData = getBookData();

        if (bookData != null && !bookData.title().isEmpty()) {
            //List<AuthorData> authorList = bookData.authorDataList();
            AuthorData authorData = bookData.authorDataList().get(0);
            Optional<Book> bookOptional = bookRepository.findByTitleIgnoreCase(bookData.title());
            Optional<Author> authorOptional = authorRepository.findByNameIgnoreCase(authorData.name());

            if (bookOptional.isEmpty()) {
                Book book = new Book(bookData);
                if (authorOptional.isEmpty()) {
                    Author author = authorRepository.save(new Author(authorData.name(),
                            authorData.birthYear(), authorData.deathYear()));
                    System.out.println("Author successfully registered!");
                    book.setAuthor(author);
                } else {
                    book.setAuthor(authorOptional.get());
                }
                bookRepository.save(book);
                System.out.println("Book successfully registered!");

                Optional<Author> registeredAuthor = authorRepository
                        .findByNameIgnoreCase(book.getAuthor().getName());

                if (registeredAuthor.isPresent()) {
                    Author author = registeredAuthor.get();
                    List<Book> authorBookList = author.getBookList();
                    if (authorBookList == null) {
                        authorBookList = new ArrayList<>();
                    }
                    authorBookList.add(book);
                    registeredAuthor.get().setBookList(authorBookList);
                }
                System.out.println(book);
            } else {
                System.out.println("The book is already registered :)");
                System.out.println("REGISTERED BOOK:");
                System.out.println(bookOptional.get());
            }
        } else {
            System.out.println("Book not found in API!!");
        }
    }

    private void showRegisteredBooks() {
        booksList = bookRepository.findAll();
        booksList.stream()
                .sorted(Comparator.comparing(b -> b.getAuthor().getId()))
                .forEach(System.out::println);
    }

    private void searchBooksByLanguage() {
        System.out.println("Enter the language of the Books you want to find: ");
        System.out.println("(en, es, fr, ja, zh, ko)");
        var l = keyboard.nextLine();

        var language = Languages.fromString(l);
        List<Book> booksByLanguage = bookRepository.findByLanguage(language);
        System.out.println("Books of in " + l + " are: ");
        booksByLanguage.forEach(System.out::println);
    }

    private void showRegisteredAuthors() {
        authorList = authorRepository.findAll();
        authorList.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void showAuthorsAliveOnAGivenYear() {
        System.out.println("Enter the year:");
        var year = keyboard.nextInt();
        Optional<List<Author>> authorsAlive = authorRepository.findAuthorsAliveInAGivenYear(year);
        if (authorsAlive.isPresent()) {
            for (Author author : authorsAlive.get()) {
                System.out.println("================================================================");
                System.out.println(author);
            }
            System.out.println("================================================================");
        }
    }

    private void searchBookByTitle() {
        System.out.println("Enter the Title of the book searched: ");
        var bookTitle = keyboard.nextLine();
        List<Book> bookFounded = bookRepository.findByTitleContainsIgnoreCase(bookTitle);
        bookFounded.forEach(l ->
                System.out.printf("Title: %s Author: %s Language: %s Downloads: %s\n",
                        l.getTitle(), l.getAuthor().getName(), l.getLanguage(), l.getDownloadCount()));
    }
}
