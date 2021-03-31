package com.example.bookshopapp.selenium.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$;

public class NavigationMenu {

    private ElementsCollection menuItems = $$(".menu_main .menu-item");

    public MainPage openMainPage() {
        menuItems.shouldHave(size(5)).first().click();
        return new MainPage();
    }

    public void openGenres() {
        menuItems.shouldHave(size(5)).get(1).click();
    }

    public void openRecent() {
        menuItems.shouldHave(size(5)).get(2).click();
    }

    public void openPopular() {
        menuItems.shouldHave(size(5)).get(3).click();
    }

    public AuthorsPage openAuthors() {
        menuItems.shouldHave(size(5)).last().click();
        return new AuthorsPage();
    }
}
