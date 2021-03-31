package com.example.bookshopapp.selenium.pages;

import com.example.bookshopapp.selenium.TestBase;

import static com.codeborne.selenide.Selenide.$;

public class AuthorsPage extends TestBase {

    public boolean isAuthorsPage() {
        return $(".Authors").isDisplayed();
    }
}
