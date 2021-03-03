package com.example.bookshopapp.security;

import com.example.bookshopapp.data.BookStoreUser;
import com.example.bookshopapp.data.BookStoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookStoreUserDetailService implements UserDetailsService {

    private final BookStoreUserRepository bookStoreUserRepository;

    @Autowired
    public BookStoreUserDetailService(BookStoreUserRepository bookStoreUserRepository) {
        this.bookStoreUserRepository = bookStoreUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<BookStoreUser> bookStoreUser = bookStoreUserRepository.findUserByEmail(s);

        if (bookStoreUser.isPresent()) {
            return new BookStoreUserDetails(bookStoreUser.get());
        } else throw new UsernameNotFoundException("user not found!");
    }
}
