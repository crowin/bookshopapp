package com.example.bookshopapp.selenium.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.bookshopapp.selenium.pages.model.ReviewUI;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BookPage extends BasePage {

    public String getBookTitle() {
        return $(".ProductCard-title").text();
    }

    public ReviewUI getReview(int index) {
        SelenideElement elem = $$(".Comment").shouldHave(sizeGreaterThanOrEqual(index + 1)).get(index);

        ReviewUI review = new ReviewUI();
        review.setAuthor(elem.find(".Comment-title").text());
        review.setComment(elem.find(".Comment-content p").text());
        review.setComment(elem.find(".Comment-content p").text());
        review.setLikes(Integer.parseInt(elem.find(".btn_like .btn-content").text()));
        review.setDislikes(Integer.parseInt(elem.find(".btn_dislike .btn-content").text()));
        return review;
    }

    public Boolean isReviewPresent(String author, String commentText) {
        return !$$(".Comment").shouldHave(sizeGreaterThan(0))
                .filter(Condition.text(author))
                .filter(Condition.text(commentText))
                .isEmpty();
    }
}
