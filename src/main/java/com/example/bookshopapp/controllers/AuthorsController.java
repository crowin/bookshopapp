package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.data.AuthorService;
import com.example.bookshopapp.data.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
@ApiModel("data model of author entity")
public class AuthorsController {

    private final AuthorService authorService;
    private final BookService booksService;

    @Autowired
    public AuthorsController(AuthorService authorService, BookService booksService) {
        this.authorService = authorService;
        this.booksService = booksService;
    }

    @GetMapping("/authors")
    public String authorsPage(Model model){
        model.addAttribute("authorsMap", authorService.getAuthorsMap());
        return "/authors/index";
    }

    @GetMapping("/authors/{author_id}")
    public String authorsPage(@PathVariable("author_id") Integer authorId, Model model) {

        model.addAttribute("authorObj",
                authorService.getAuthorById(authorId)
                        .setBookList(booksService.getPageOfBooksByAuthorId(authorId, 0, 5).getContent()));
        model.addAttribute("authorTotalBooks", booksService.getTotalBooksByAuthorId(authorId));

        return "authors/slug";
    }

    @GetMapping("books/author/{author_id}")
    public String authorsBooksPage(@PathVariable("author_id") Integer authorId, Model model) {

        model.addAttribute("authorObj",
                authorService.getAuthorById(authorId)
                        .setBookList(booksService.getPageOfBooksByAuthorId(authorId, 0, 20).getContent()));
        return "books/author";
    }


    @ApiOperation("get all authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Author>> authors() {
        return authorService.getAuthorsMap();
    }
}
