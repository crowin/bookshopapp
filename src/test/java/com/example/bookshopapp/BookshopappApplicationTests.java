package com.example.bookshopapp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookshopappApplicationTests {

    @Value("${auth.secret}")
    String authSecret;

    private final BookshopappApplicationTests application;

    @Autowired
    public BookshopappApplicationTests(BookshopappApplicationTests application) {
        this.application = application;
    }

    @Test
    void contextLoads() {
        assertNotNull(application);
    }

    @Test
    void verifyAuthSecret(){
        assertThat(authSecret, Matchers.containsString("box"));
    }

}
