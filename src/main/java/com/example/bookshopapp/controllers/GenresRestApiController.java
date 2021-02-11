package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/genre")
@Api(description = "genres data api")
public class GenresRestApiController {
    private GenreService genreService;

    @Autowired
    public GenresRestApiController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{genreId}")
    public BooksPageDto getGenreWithBookList(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit,
                                           @PathVariable(value = "genreId") Integer genreId) {
        Genre genre = genreService.getGenreById(genreId);
        PagedListHolder<Book> page = new PagedListHolder<>(genre.getBooks());
        page.setPageSize(limit);
        page.setPage(offset);

        return new BooksPageDto(page.getPageList());
    }
}
