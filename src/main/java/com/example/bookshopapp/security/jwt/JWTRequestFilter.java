package com.example.bookshopapp.security.jwt;

import com.example.bookshopapp.security.BookstoreUserDetails;
import com.example.bookshopapp.security.BookstoreUserDetailsService;
import com.example.bookshopapp.services.TokensService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final TokensService tokensService;
    private final JWTUtil jwtUtil;

    public JWTRequestFilter(BookstoreUserDetailsService bookstoreUserDetailsService, TokensService tokensService, JWTUtil jwtUtil) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.tokensService = tokensService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token;
        String username;
        Cookie tokenCookie = httpServletRequest.getCookies() != null ? Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst().orElse(null) : null;

        if (tokenCookie != null) {
            token = tokenCookie.getValue();
            username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                BookstoreUserDetails userDetails = bookstoreUserDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token, userDetails) && !tokensService.isTokenRevoked(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
