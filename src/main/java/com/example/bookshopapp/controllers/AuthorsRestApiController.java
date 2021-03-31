package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.*;
import com.example.bookshopapp.errs.BookstoreApiWrongParameterException;
import com.example.bookshopapp.services.AuthorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorsRestApiController {
    private final AuthorService authorService;

    @Autowired
    public AuthorsRestApiController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{author_id}")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<Author> getByAuthorId(@PathVariable(name = "author_id") Integer authorId)
            throws BookstoreApiWrongParameterException {
        Author data = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(data);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad parameter value...", exception)
                , HttpStatus.BAD_REQUEST);
    }
}
