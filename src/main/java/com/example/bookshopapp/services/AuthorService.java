package com.example.bookshopapp.services;

import com.example.bookshopapp.data.Author;
import com.example.bookshopapp.repositories.AuthorRepository;
import com.example.bookshopapp.errs.BookstoreApiWrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private JdbcTemplate jdbcTemplate;
    private AuthorRepository authorRepository;


    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate, AuthorRepository authorRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorRepository = authorRepository;
    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = jdbcTemplate.query("SELECT * FROM authors",(ResultSet rs, int rowNum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setFirstName(rs.getString("first_name"));
            author.setLastName(rs.getString("last_name"));
            return author;
        });

        return authors.stream().collect(Collectors.groupingBy((Author a) -> {return a.getLastName().substring(0,1);}));
    }

    public Author getAuthorById(Integer authorId) throws BookstoreApiWrongParameterException {
        if (authorId == null) throw new BookstoreApiWrongParameterException("Empty author id");
        return authorRepository.findById(authorId).orElse(null);
    }

}