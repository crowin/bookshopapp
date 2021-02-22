package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.data.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

//@Controller
//@RequestMapping("/books")
public class BookshopPostponedController {
//
//    @ModelAttribute(name = "bookPostponed")
//    public List<Book> bookPostponed() {
//        return new ArrayList<>();
//    }
//
//    private final BookRepository bookRepository;
//
//    //@Autowired
//    public BookshopPostponedController(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    @GetMapping("/postponed")
//    public String handlePostponedRequest(@CookieValue(value = "postponedContents", required = false) String postponedContents,
//                                    Model model) {
//        if (postponedContents == null || postponedContents.equals("")) {
//            model.addAttribute("isPostponedEmpty", true);
//        } else {
//            model.addAttribute("isPostponedEmpty", false);
//            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
//            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length() - 1) : postponedContents;
//            String[] cookieSlugs = postponedContents.split("/");
//            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
//            model.addAttribute("bookPostponed", booksFromCookieSlugs);
//            model.addAttribute("bookPostponedIds", postponedContents.replaceAll("/", ", "));
//        }
//
//        return "postponed";
//    }
//
//    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
//    public String handleRemoveBookFromPostponedRequest(@PathVariable("slug") String slug, @CookieValue(name =
//            "postponedContents", required = false) String postponedContents, HttpServletResponse response, Model model){
//
//        if (postponedContents != null && !postponedContents.equals("")){
//            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
//            cookieBooks.remove(slug);
//            Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("isPostponedEmpty", false);
//        }else {
//            model.addAttribute("isPostponedEmpty", true);
//        }
//
//        return "redirect:/books/postponed";
//    }
//
//    @PostMapping("/changeBookStatus/{slug}")
//    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "postponedContents",
//            required = false) String postponedContents, HttpServletResponse response, Model model) {
//
//        if (postponedContents == null || postponedContents.equals("")) {
//            Cookie cookie = new Cookie("postponedContents", slug);
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("ispostponedEmpty", false);
//        } else if (!postponedContents.contains(slug)) {
//            StringJoiner stringJoiner = new StringJoiner("/");
//            stringJoiner.add(postponedContents).add(slug);
//            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("ispostponedEmpty", false);
//        }
//
//        return "redirect:/books/" + slug;
//    }
}
