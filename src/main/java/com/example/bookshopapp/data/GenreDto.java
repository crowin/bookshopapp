package com.example.bookshopapp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter @Setter @Accessors(chain = true)
public class GenreDto {
    private Integer id;
    private String name;
    private String slug;
    private List<GenreDto> children;
}
