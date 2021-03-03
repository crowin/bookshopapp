package com.example.bookshopapp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookStoreUserRepository extends JpaRepository<BookStoreUser, Integer> {

    Optional<BookStoreUser> findUserByName(String name);

    Optional<BookStoreUser> findUserByEmail(String email);
}
