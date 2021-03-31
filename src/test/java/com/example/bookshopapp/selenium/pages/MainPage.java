package com.example.bookshopapp.selenium.pages;


import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage extends BasePage {

    public BookPage clickRecommendedBook(int index) {
        $$("[data-load='recommended'] div").shouldHave(sizeGreaterThanOrEqual(index + 1)).get(index).click();
        return new BookPage();
    }

    public boolean isMainPage() {
        return $("[data-load='recommended']").isDisplayed()
                && $("[data-load='recent']").isDisplayed()
                && $("[data-load='popular']").isDisplayed();
    }


}
