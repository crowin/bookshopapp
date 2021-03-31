package com.example.bookshopapp.selenium;

import com.example.bookshopapp.selenium.pages.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PagesNavigationTest extends TestBase {

    @Test
    void checkMainPage() {
        Assertions.assertTrue(
                new MainPage().topNavigationMenu().openMainPage().isMainPage(), "It isn't main page"
        );
    }

    @Test
    void checkAuthorsPage() {
        Assertions.assertTrue(
                new MainPage().topNavigationMenu().openAuthors().isAuthorsPage(), "It isn't authors page"
        );
    }
}
