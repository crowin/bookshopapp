package com.example.bookshopapp.selenium;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {


    private final String baseUrl = "http://localhost:8085/";

    @BeforeEach
    void open() {
        Selenide.open("http://localhost:8085/");
    }
}
