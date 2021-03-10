package com.example.bookshopapp.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends JpaRepository<TokenDao, Integer> {

//    TokenDao findTokenDaoByTokenAndUser(String token, BookstoreUser user);

    TokenDao findTokenDaoByToken(String token);

    TokenDao findTokenDaoByUser(BookstoreUser user);
}
