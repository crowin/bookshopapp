package com.example.bookshopapp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String bookName) {
        return bookRepository.findBooksByTitleContaining(bookName);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price) {
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    public Page<Book> getBestsellers(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByIsBestsellerEquals(1, nextPage);
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByTitleContaining(searchWord, nextPage);
    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByPubDateAfter(
                Date.from(Instant.now().minus(100, ChronoUnit.DAYS)),
                nextPage);
    }

    public Page<Book> getPageOfRecentBooksByDates(Date startedDate, Date finishedDate, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByPubDateBetween(startedDate, finishedDate, nextPage);
    }

    public Page<Book> getPageOfBooksByAuthorId(Integer authorId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBooksByAuthorIdEquals(authorId, nextPage);
    }

    public Book getBookById(Integer id) {
        return bookRepository.findBookByIdEquals(id);
    }

    public Integer getTotalBooksByAuthorId(Integer authorId) {
        return bookRepository.countBookByAuthorIdEquals(authorId);
    }

    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getPageOfPopularBooks(nextPage);
    }
}
