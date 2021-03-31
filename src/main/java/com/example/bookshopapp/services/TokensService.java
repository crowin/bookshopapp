package com.example.bookshopapp.services;

import com.example.bookshopapp.repositories.TokensRepository;
import com.example.bookshopapp.security.BookstoreUser;
import com.example.bookshopapp.security.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokensService {
    private final TokensRepository tokensRepository;

    @Autowired
    public TokensService(TokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
    }

    public boolean isTokenRevoked(String token) {
        TokenDao dao = tokensRepository.findTokenDaoByToken(token);
        return dao != null && dao.getRevokedAt() != null;
    }

    public TokenDao saveToken(String token, BookstoreUser user) {
        TokenDao dao = findToken(user);

        if (dao == null) {
            dao = new TokenDao(token, user);
        } else dao.setToken(token);

        return tokensRepository.saveAndFlush(dao);
    }

    public TokenDao findToken(BookstoreUser user) {
        return tokensRepository.findTokenDaoByUser(user);
    }

    public TokenDao updateToken(TokenDao tokenDao) {
        return tokensRepository.save(tokenDao);
    }

    public TokenDao findToken(String token) {
        return tokensRepository.findTokenDaoByToken(token);
    }
}
