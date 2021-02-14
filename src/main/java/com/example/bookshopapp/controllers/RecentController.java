package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class RecentController {

    private final BookService bookService;

    @Autowired
    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        Date defaultStartedTime = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));
        Date defaultFinishedTime = new Date();
        return bookService.getPageOfRecentBooksByDates(defaultStartedTime, defaultFinishedTime, 0, 20)
                .getContent();
    }

    @GetMapping("/news")
    public String authorsPage(){
        return "/books/recent";
    }
}
