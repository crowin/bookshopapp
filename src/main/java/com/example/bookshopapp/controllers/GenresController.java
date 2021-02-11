package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.Genre;
import com.example.bookshopapp.data.GenreDto;
import com.example.bookshopapp.data.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String genresPage(Model model, @RequestParam(required = false) Optional<String> lang){
        String currentLocale = !lang.isPresent() || lang.get().isEmpty() ? "en" : lang.get();
        model.addAttribute("genresList", genreService.getBooksCountByGenres(currentLocale));
        return "/genres/index";
    }

    @GetMapping("/genres/{slug}")
    public String genrePage(
            @PathVariable("slug") String slug,
            @RequestParam(required = false) Optional<String> lang,
            Model model) {
        Genre genre = genreService.getGenreWithBooksList(slug);
        Genre parent = genreService.getGenreById(genre.getParentId());

        genre.setCurrentName(!lang.isPresent() || lang.get().isEmpty() ? "en" : lang.get());
        genre.setTotalBooksCount(genre.getBooks().size());

        String name = getLanguage(lang, genre);
        String parentName = getLanguage(lang, parent);


        PagedListHolder<Book> page = new PagedListHolder<>(genre.getBooks());
        page.setPage(0);
        page.setPageSize(10);

        model.addAttribute("genre", new GenreDto(genre.getId(), name, genre.getBooks().size(), page.getPageList(), parentName));
        return "genres/slug";
    }

    private String getLanguage(Optional<String> lang, Genre genre) {
        return !lang.isPresent() || lang.get().isEmpty() || lang.get().equals("en") ? genre.getNameEnLocale() : genre.getNameRuLocale();
    }

}
