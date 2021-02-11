package com.example.bookshopapp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT new com.example.bookshopapp.data.Author(a.id, a.firstName, a.lastName) from Author a where a.id = ?1")
    public Author findAuthorInfo(Integer authorId);
}
