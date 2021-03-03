package com.example.bookshopapp.security;

import com.example.bookshopapp.data.User;
import com.example.bookshopapp.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookStoreUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BookStoreUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> bookStoreUser = userRepository.findUserByEmail(s);

        if (bookStoreUser.isPresent()) {
            return new BookStoreUserDetails(bookStoreUser.get());
        } else throw new UsernameNotFoundException("user not found!");
    }
}
