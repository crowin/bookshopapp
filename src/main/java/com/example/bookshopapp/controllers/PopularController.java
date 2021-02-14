package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PopularController {

    private final BookService bookService;

    @Autowired
    public PopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookService.getBestsellers(0, 20).getContent();
    }

    @GetMapping("/popular")
    public String authorsPage(){
        return "/books/popular";
    }
}
