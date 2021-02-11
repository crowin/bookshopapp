package com.example.bookshopapp.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BooksPageDto {
    private Integer count;
    private List<Book> books;

    public BooksPageDto(List<Book> content) {
        this.count = content.size();
        this.books = new ArrayList<>(content);
    }
}
