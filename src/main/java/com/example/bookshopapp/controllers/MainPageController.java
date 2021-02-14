package com.example.bookshopapp.controllers;


import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookService;
import com.example.bookshopapp.data.BooksPageDto;
import com.example.bookshopapp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 20).getContent();
    }

    @ModelAttribute("recentlyBooks")
    public List<Book> recentlyBooks() {
        return bookService.getPageOfRecentBooks(0, 20).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookService.getBestsellers(0, 20).getContent();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) {
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResults",
                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 20).getContent());
        return "/search/index";
    }
}
