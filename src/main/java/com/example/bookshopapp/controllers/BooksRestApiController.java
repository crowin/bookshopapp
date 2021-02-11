package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookService;
import com.example.bookshopapp.data.BooksPageDto;
import com.example.bookshopapp.data.SearchWordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.bookshopapp.utils.BookDateTimeUtils.*;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BooksRestApiController {

    private final BookService bookService;

    @Autowired
    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/by-author")
    @ApiOperation("get books list of bookshop by author first name")
    public ResponseEntity<List<Book>> bookByAuthor(@RequestParam("author") String authorName) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }

    @GetMapping("/books/by-title")
    @ApiOperation("get books list of bookshop by book title")
    public ResponseEntity<List<Book>> bookByTitle(@RequestParam("title") String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("get book list of bookshop by price range")
    public ResponseEntity<List<Book>> priceRangeBooks(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
    }

    @GetMapping("/books/with-max-discount")
    @ApiOperation("get book list of bookshop by max discount")
    public ResponseEntity<List<Book>> priceRangeBooks() {
        return ResponseEntity.ok(bookService.getBooksWithMaxPrice());
    }

    @GetMapping("/books/popular")
    @ApiOperation("get bestsellers list")
    public ResponseEntity<BooksPageDto> bestsellers(@RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(new BooksPageDto(bookService.getBestsellers(offset, limit).getContent()));
    }

    @GetMapping("/books/recommended")
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @SneakyThrows
    @GetMapping("/books/recent")
    public BooksPageDto getRecentBooksPage(
            @RequestParam(value = "from", required = false) Optional<String> from,
            @RequestParam(value = "to", required = false) Optional<String> to,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
        if (from.isPresent() && to.isPresent()) {
            return new BooksPageDto(bookService.getPageOfRecentBooksByDates(
                    parseDate(from.get()), parseDate(to.get()), offset, limit).getContent()
            );
        }
        return new BooksPageDto(bookService.getPageOfRecentBooks(offset, limit).getContent());
    }


    @GetMapping("/search/page/{searchWord}")
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }

    @GetMapping("/books/author/{author_id}")
    public ResponseEntity<BooksPageDto> bookByAuthor(@PathVariable("author_id") Integer authorId,
                                                   @RequestParam("offset") Integer offset,
                                                   @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(new BooksPageDto(bookService.getPageOfBooksByAuthorId(authorId, offset, limit).getContent()));
    }

}
