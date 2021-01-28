package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
public class DocumentsController {

    private final AuthorService authorService;

    @Autowired
    public DocumentsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("/documents")
    public String authorsPage(){
        return "documents/index";
    }
}
