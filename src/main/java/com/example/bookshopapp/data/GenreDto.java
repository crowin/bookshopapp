package com.example.bookshopapp.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter @Setter @Accessors(chain = true) @AllArgsConstructor
public class GenreDto {
    private Integer id;
    private String name;
    private Integer count;
    private List<Book> books;
    private String parentName;
}
