package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.Book;
import com.example.bookshopapp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BaseBookshopCartController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "bookPostponed")
    public List<Book> bookPostponed() {
        return new ArrayList<>();
    }

    private final BookRepository bookRepository;

    @Autowired
    public BaseBookshopCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = {"/cart", "/postponed"})
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    @CookieValue(value = "postponedContents", required = false) String postponedContents,
                                    HttpServletRequest request,
                                    Model model) {
        boolean isCartRequest = request.getRequestURI().contains("cart");

        if (isCartRequest) {
            prepareRequestContent(cartContents, model, isCartRequest);
        } else {
            prepareRequestContent(postponedContents, model, isCartRequest);
        }

        return isCartRequest ? "cart" : "postponed";
    }

    @PostMapping(value = {"/changeBookStatus/cart/remove/{slug}", "/changeBookStatus/postponed/remove/{slug}"})
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
                                                  @CookieValue(name = "cartContents", required = false) String cartContents,
                                                  @CookieValue(value = "postponedContents", required = false) String postponedContents,
                                                  HttpServletResponse response,
                                                  HttpServletRequest request,
                                                  Model model){
        boolean isCartRequest = request.getRequestURI().contains("cart");

        if (isCartRequest) {
            preparePostRequestContent(slug, cartContents, response, model, isCartRequest);
        } else {
            preparePostRequestContent(slug, postponedContents, response, model, isCartRequest);
        }

        return isCartRequest ? "redirect:/books/cart" : "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug,
                                         @CookieValue(name = "cartContents", required = false) String cartContents,
                                         @CookieValue(value = "postponedContents", required = false) String postponedContents,
                                         HttpServletResponse response,
                                         Model model) {
        String postponedPostfix = "-postponed";
        if (slug.contains(postponedPostfix)) {
            prepareChangeStatusRequest(slug.replace(postponedPostfix, ""), postponedContents, response, model, "isPostponedEmpty", "postponedContents");
        } else prepareChangeStatusRequest(slug, cartContents, response, model, "isCartEmpty", "cartContents");

        return "redirect:/books/" + slug.replace(postponedPostfix, "");
    }

//    @PostMapping("/changeBookStatus")
//    public String handleRateBookStatus(@RequestBody Map<String, String> formData) {
//        bookRepository.
//    }

    private void prepareChangeStatusRequest(String slug, String currentCookie, HttpServletResponse response, Model model, String attribute, String contentType) {
        if (currentCookie == null || currentCookie.equals("")) {
            Cookie cookie = new Cookie(contentType, slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(attribute, false);
        } else if (!currentCookie.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(currentCookie).add(slug);
            Cookie cookie = new Cookie(contentType, stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(attribute, false);
        }
    }

    private void prepareRequestContent(String contentType, Model model, boolean isCartRequest) {
        if (contentType == null || contentType.equals("")) {
            model.addAttribute(isCartRequest ? "isCartEmpty" : "isPostponedEmpty", true);
        } else {
            model.addAttribute(isCartRequest ? "isCartEmpty"  : "isPostponedEmpty", false);
            contentType = contentType.startsWith("/") ? contentType.substring(1) : contentType;
            contentType = contentType.endsWith("/") ? contentType.substring(0, contentType.length() - 1) : contentType;
            String[] cookieSlugs = contentType.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute(isCartRequest ? "bookCart" : "bookPostponed",
                    booksFromCookieSlugs.stream().sorted(Comparator.comparingDouble(Book::rating).reversed()).collect(Collectors.toList()));
            if (!isCartRequest) model.addAttribute("bookPostponedIds", contentType.replaceAll("/", ", "));

        }
    }

    private void preparePostRequestContent(String slug, String content, HttpServletResponse response, Model model, boolean isCartRequest) {
        if (content != null && !content.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(content.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie(isCartRequest ? "cartContents" : "postponedContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(isCartRequest ? "isCartEmpty"  : "isPostponedEmpty", false);
        }else {
            model.addAttribute(isCartRequest ? "isCartEmpty"  : "isPostponedEmpty", true);
        }
    }
}
