package com.example.bookshopapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    public BookstoreUserDetailsService(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Override
    public BookstoreUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<BookstoreUser> bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(s);

        return new BookstoreUserDetails(
                bookstoreUser.orElseThrow(() -> new UsernameNotFoundException("user not found doh!"))
        );
    }
}
