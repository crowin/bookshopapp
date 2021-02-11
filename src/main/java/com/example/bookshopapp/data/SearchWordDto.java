package com.example.bookshopapp.data;

import lombok.Data;

@Data
public class SearchWordDto {

    private String example;

    public SearchWordDto(String example) {
        this.example = example;
    }

    public SearchWordDto() { }
}
