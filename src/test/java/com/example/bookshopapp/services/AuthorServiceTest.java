package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.errs.BookstoreApiWrongParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AuthorServiceTest {

    private AuthorService authorService;

    @Autowired
    public AuthorServiceTest(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Test
    void getAuthorsMap() {
        Map<String, List<Author>> map = authorService.getAuthorsMap();
        Assertions.assertNotNull(map);

        Author author = map.get("P").get(0);
        Assertions.assertEquals(author.getFirstName() + " " + author.getLastName(), "Kylen Powers");
    }

    @Test
    void getAuthorById() throws BookstoreApiWrongParameterException {
        Author author = authorService.getAuthorById(1);
        Assertions.assertEquals(author.getFirstName() + " " + author.getLastName(), "Kylen Powers");
    }
}