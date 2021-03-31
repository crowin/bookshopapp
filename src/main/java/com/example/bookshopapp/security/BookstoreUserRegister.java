package com.example.bookshopapp.security;

import com.example.bookshopapp.errs.BadUserAuthorization;
import com.example.bookshopapp.repositories.BookstoreUserRepository;
import com.example.bookshopapp.repositories.NetworkAuthRepository;
import com.example.bookshopapp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserRegister {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final NetworkAuthRepository networkAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository, NetworkAuthRepository networkAuthRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.networkAuthRepository = networkAuthRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public BookstoreUser registerNewUser(RegistrationForm registrationForm) {
        BookstoreUser user = null;

        if (!bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail()).isPresent()) {
            user = new BookstoreUser();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            bookstoreUserRepository.save(user);
        }
        return user;
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public Object getCurrentUser() throws BadUserAuthorization {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken currentAuth = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            BookstoreUser daoUser;
            NetworkAuth networkAuth;

            switch (currentAuth.getAuthorizedClientRegistrationId()) {
                case "facebook":
                    networkAuth = networkAuthRepository.findNetworkAuthByNetworkId(currentAuth.getPrincipal().getAttributes().get("id").toString());
                    //daoUser = bookstoreUserRepository.findBookstoreUserByEmail(currentAuth.getPrincipal().getAttributes().get("email").toString()).orElse(null);
                    break;
                default: throw new BadUserAuthorization("Something wrong happened during user search");
            }

            if (networkAuth == null) {
                daoUser = new BookstoreUser();
                daoUser.setEmail(currentAuth.getPrincipal().getAttributes().get("email").toString());
                daoUser.setName(currentAuth.getPrincipal().getAttributes().get("name").toString());
                daoUser = bookstoreUserRepository.saveAndFlush(daoUser);

                networkAuth = new NetworkAuth();
                networkAuth.setNetworkId(currentAuth.getPrincipal().getAttributes().get("id").toString());
                networkAuth.setNetworkName(currentAuth.getAuthorizedClientRegistrationId());
                networkAuth.setUser(daoUser);
                networkAuthRepository.save(networkAuth);

                return daoUser;
            } else return networkAuth.getUser();
        } else {
            BookstoreUserDetails userDetails =
                    (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getBookstoreUser();
        }
    }
}
