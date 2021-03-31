package com.example.bookshopapp.selenium;

import com.example.bookshopapp.selenium.pages.BookPage;
import com.example.bookshopapp.selenium.pages.MainPage;
import com.example.bookshopapp.selenium.pages.model.ReviewUI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReviewsTest extends TestBase {
    @Test
    void verifyExpectedReview() {
        int bookIndex = 0;
        int reviewIndex = 0;
        String expectedAuthor = "wmcilmurray0";
        String expectedComment = "Maecenas tincidunt lacus at velit.";
        int expectedLikes = 0;
        int expectedDislikes = 0;

        BookPage page = new MainPage().clickRecommendedBook(bookIndex);
        ReviewUI actualReview = page.getReview(reviewIndex);

        Assertions.assertEquals(expectedAuthor, actualReview.getAuthor());
        Assertions.assertEquals(expectedComment, actualReview.getComment());
        Assertions.assertEquals(expectedLikes, actualReview.getLikes());
        Assertions.assertEquals(expectedDislikes, actualReview.getDislikes());
    }

    @Test
    void verifyReviewOfAnotherNotPresent() {
        int bookIndex = 0;
        String author = "Дмитрий Петров";
        String comment = "Review of another user";
        boolean isPresent = new MainPage().clickRecommendedBook(bookIndex).isReviewPresent(author, comment);

        Assertions.assertFalse(isPresent);

    }
}
