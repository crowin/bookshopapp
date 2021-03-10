package com.example.bookshopapp.security;

import com.example.bookshopapp.errs.BadUserAuthorization;
import com.example.bookshopapp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;
    private final BookstoreUserRepository bookstoreUserRepository;
    private final TokensService tokensService;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister, BookstoreUserRepository bookstoreUserRepository, TokensService tokensService, JWTUtil jwtUtil) {
        this.userRegister = userRegister;
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.tokensService = tokensService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/signin")
    public String handleSignIn(){
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model){
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
//        if (isUserFound) {
//            response.setResult("true");
//        } else {
//            throw new BadUserAuthorization("Filled contact is bad");
//        }
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model){
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public  ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                    HttpServletResponse httpServletResponse){
        Cookie cookie;
        ContactConfirmationResponse loginResponse = new ContactConfirmationResponse();
        BookstoreUser user = bookstoreUserRepository.findBookstoreUserByEmail(payload.getContact()).get();

        if (user.getToken() != null && jwtUtil.validateToken(user.getToken().getToken(), new BookstoreUserDetails(user)) && user.getToken() != null && user.getToken().getRevokedAt() == null) {
            cookie = new Cookie("token", user.getToken().getToken());
            loginResponse.setResult(user.getToken().getToken());
        } else {
            loginResponse = userRegister.jwtLogin(payload);
            cookie = new Cookie("token", loginResponse.getResult());
            httpServletResponse.addCookie(cookie);
            tokensService.saveToken(loginResponse.getResult(), bookstoreUserRepository.findBookstoreUserByEmail(payload.getContact()).get());
        }
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }

    @GetMapping("/my")
    public String handleMy(){
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        return "profile";
    }

    @ExceptionHandler(value = {BadUserAuthorization.class, BadCredentialsException.class})
    public ResponseEntity<ContactConfirmationResponse> handleBadCredentialsByJWT(Exception exception) {
        ContactConfirmationResponse resp = new ContactConfirmationResponse();
        resp.setError("Invalid contact or password. " + exception.getMessage());
        return ResponseEntity.ok(resp);
    }

//    @GetMapping("/logout")
//    public void handleLogout(HttpServletRequest request){
//        Cookie cookie = request.getCookies() != null ? Arrays.stream(request.getCookies())
//                .filter(c -> c.getName().equals("token"))
//                .findFirst().orElse(null) : null;
//
//        if (cookie != null && tokensService.isTokenExist(cookie.getValue())) {
//            tokensService.revokeToken(cookie.getValue());
//        }
//    }
}
