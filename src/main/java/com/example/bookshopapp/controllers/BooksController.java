package com.example.bookshopapp.controllers;


import com.example.bookshopapp.data.BookService;
import com.example.bookshopapp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BooksController {

    private BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{book_id}")
    public String getSearchResults(@PathVariable("book_id") Integer bookId,
                                   Model model) {
        model.addAttribute("book", bookService.getBookById(bookId));
        return "/books/slug";
    }
}
