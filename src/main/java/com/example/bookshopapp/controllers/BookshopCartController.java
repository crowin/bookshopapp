package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

//@Controller
//@RequestMapping("/books")
public class BookshopCartController {
//
//    @ModelAttribute(name = "bookCart")
//    public List<Book> bookCart() {
//        return new ArrayList<>();
//    }
//
//    private final BookRepository bookRepository;
//
//    //@Autowired
//    public BookshopCartController(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    @GetMapping(value = {"/cart", "postponed"})
//    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
//                                    Model model,
//                                    HttpServletRequest request) {
//        if (cartContents == null || cartContents.equals("")) {
//            model.addAttribute("isCartEmpty", true);
//        } else {
//            model.addAttribute("isCartEmpty", false);
//            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
//            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
//            String[] cookieSlugs = cartContents.split("/");
//            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
//            model.addAttribute("bookCart", booksFromCookieSlugs);
//        }
//
//        return request.getRequestURI().contains("cart") ? "cart" : "postponed";
//    }
//
//    @PostMapping(value = {"/changeBookStatus/cart/remove/{slug}", "/changeBookStatus/postponed/remove/{slug}"})
//    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
//                                                  @CookieValue(name = "cartContents", required = false) String cartContents,
//                                                  HttpServletResponse response,
//                                                  HttpServletRequest request,
//                                                  Model model){
//
//        if (cartContents != null && !cartContents.equals("")){
//            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
//            cookieBooks.remove(slug);
//            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("isCartEmpty", false);
//        }else {
//            model.addAttribute("isCartEmpty", true);
//        }
//
//        return request.getRequestURI().contains("cart") ? "redirect:/books/cart" : "redirect:/books/postponed";
//    }
//
//    @PostMapping("/changeBookStatus/{slug}")
//    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
//            required = false) String cartContents, HttpServletResponse response, Model model) {
//
//        if (cartContents == null || cartContents.equals("")) {
//            Cookie cookie = new Cookie("cartContents", slug);
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("isCartEmpty", false);
//        } else if (!cartContents.contains(slug)) {
//            StringJoiner stringJoiner = new StringJoiner("/");
//            stringJoiner.add(cartContents).add(slug);
//            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("isCartEmpty", false);
//        }
//
//        return "redirect:/books/" + slug;
//    }
}
