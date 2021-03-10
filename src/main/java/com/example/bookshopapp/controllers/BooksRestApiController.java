package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.*;
import com.example.bookshopapp.errs.BookstoreApiWrongParameterException;
import com.example.bookshopapp.security.BookstoreUser;
import com.example.bookshopapp.security.BookstoreUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BooksRestApiController {

    private final BookService bookService;
    private final ReviewRepository reviewRepository;
    private final BookstoreUserRepository bookStoreUserRepository;


    @Autowired
    public BooksRestApiController(BookService bookService, ReviewRepository reviewRepository, BookstoreUserRepository bookStoreUserRepository) {
        this.bookService = bookService;
        this.reviewRepository = reviewRepository;
        this.bookStoreUserRepository = bookStoreUserRepository;
    }

    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName) {
        List<Book> data = bookService.getBooksByAuthor(authorName);
        for (Book book: data) {
            Link link = linkTo(AuthorsRestApiController.class).slash(book.getAuthor().getId()).withRel("author");
            book.add(link);
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/books/by-title")
    @ApiOperation("get books by book title")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful request"),

    })
    public ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("get book by price range from min price to max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@RequestParam("min") Integer min,
                                                      @RequestParam("max") Integer max) {
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
    }

    @GetMapping("/books/with-max-discount")
    @ApiOperation("get list of book with max price")
    public ResponseEntity<List<Book>> maxPriceBooks() {
        return ResponseEntity.ok(bookService.getBooksWithMaxPrice());
    }

    @GetMapping("/books/bestsellsers")
    @ApiOperation("get bestseller books (which is_bestseller = 1)")
    public ResponseEntity<List<Book>> bestSellerBooks() {
        return ResponseEntity.ok(bookService.getBestsellers());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Missing required parameters",
                exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad parameter value...", exception)
                , HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/books/bookReview", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Map<String, Object>> postReview(HttpServletRequest request) {
        String[] reviewText = request.getParameterMap().get("text");
        Book book = bookService.getById(Integer.valueOf(request.getParameterMap().get("bookId")[0]));

        if (reviewText.length != 0) {

            BookstoreUser mockBookstoreUser = bookStoreUserRepository.findAll().get(0);
            Review review = new Review();
            review.setBook(book);
            review.setBookStoreUser(mockBookstoreUser);
            review.setText(reviewText[0]);
            review.setTime(new Date());
            reviewRepository.saveAndFlush(review);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result", true);
        map.put("error", null);
        return ResponseEntity.ok(map);
    }

}
