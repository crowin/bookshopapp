package com.example.bookshopapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

@Component
public class LogoutTokenListHandler implements LogoutHandler {
    private final TokensService tokensService;

    @Autowired
    public LogoutTokenListHandler(TokensService tokensService) {
        this.tokensService = tokensService;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Cookie cookie = extractTokenCookie(httpServletRequest);
        if (cookie != null) {
            TokenDao tokenDao = tokensService.findToken(((BookstoreUserDetails)authentication.getPrincipal()).getBookstoreUser());
            if (tokenDao != null) {
                tokenDao.setRevokedAt(new Date());
                tokensService.updateToken(tokenDao);
            }
        }
    }

    private Cookie extractTokenCookie(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getCookies() != null ? Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst().orElse(null) : null;
    }
}
