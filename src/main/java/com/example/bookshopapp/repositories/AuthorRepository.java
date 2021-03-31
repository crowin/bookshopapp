package com.example.bookshopapp.repositories;

import com.example.bookshopapp.data.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
