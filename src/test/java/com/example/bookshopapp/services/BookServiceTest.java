package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.errs.BookstoreApiWrongParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookServiceTest {

    BookService bookService;

    @Autowired
    public BookServiceTest(BookService bookService) {
        this.bookService = bookService;
    }

    @Test
    void getBooksData() {
        List<Book> books = bookService.getBooksData();
        Assertions.assertNotNull(books);
        Assertions.assertTrue(books.size() > 0);
    }

    @Test
    void getBooksByAuthor() {
        List<Book> books = bookService.getBooksByAuthor("Kylen");
        Assertions.assertNotNull(books);
        Assertions.assertTrue(books.size() > 0);
        Assertions.assertEquals(books.get(0).getAuthor().getFirstName(), "Kylen");
    }

    @Test
    void getBooksByTitle() throws BookstoreApiWrongParameterException {
        List<Book> books = bookService.getBooksByTitle("Point Break");
        Assertions.assertNotNull(books);
        Assertions.assertTrue(books.size() > 0);
        Assertions.assertEquals(books.get(0).getTitle(), "Point Break");

    }
}