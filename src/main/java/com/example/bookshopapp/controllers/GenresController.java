package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class GenresController {

    private GenreService genreService;

    @Autowired
    public GenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String authorsPage(Model model, @RequestParam(required = false) Optional<String> lang){
        String currentLocale = !lang.isPresent() || lang.get().isEmpty() ? "en" : lang.get();
        model.addAttribute("genresList", genreService.getBooksCountByGenres(currentLocale));
        return "/genres/index";
    }
}
