package com.example.bookshopapp.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBookByAuthor_FirstName(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();

    //NEW BOOK REST REPOSITORY

    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book>findBooksByTitleContaining(String bookTitle);

    Book findBookByIdEquals(Integer bookId);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    Page<Book> findBooksByIsBestsellerEquals(Integer value, Pageable nextPage);

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM Books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBooksByTitleContaining(String bookTitle, Pageable nextPage);

    Page<Book> findBooksByPubDateAfter(Date date, Pageable nextPage);

    Page<Book> findBooksByPubDateBetween(Date startedDate, Date finishedDate, Pageable nextPage);

    Page<Book> findBooksByAuthorIdEquals(Integer authorId, Pageable nextPage);
}
