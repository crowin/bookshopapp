package com.example.bookshopapp.selenium.pages.model;

import lombok.Data;

@Data
public class ReviewUI {
    private String author;
    private String comment;
    private Integer likes;
    private Integer dislikes;
}
