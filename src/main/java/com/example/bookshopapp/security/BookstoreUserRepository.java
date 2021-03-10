package com.example.bookshopapp.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookstoreUserRepository extends JpaRepository<BookstoreUser, Integer> {

    Optional<BookstoreUser> findBookstoreUserByName(String name);

    Optional<BookstoreUser> findBookstoreUserByEmail(String email);
}
