package com.example.bookshopapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BaseBookshopCartControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public BaseBookshopCartControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void handlePostponedWithBookRequest() throws Exception {
        mockMvc.perform(get("/books/postponed").cookie(new Cookie("postponedContents", "book-fck-791")))
                .andDo(print())
                .andExpect(content().string(containsString("Carnages (a.k.a. Carnage)")))
                .andExpect(status().isOk());
    }

    @Test
    void handleCartWithBooksRequest() throws Exception {
        mockMvc.perform(get("/books/cart").cookie(new Cookie("cartContents", "book-apv-767/book-nes-960/book-fck-791")))
                .andDo(print())
                .andExpect(content().string(containsString("Carnages (a.k.a. Carnage)")))
                .andExpect(content().string(containsString("Daar")))
                .andExpect(content().string(containsString("Goddess, The (Shen nu)")))
                .andExpect(status().isOk());
    }

    @Test
    void handleEmptyCartRequest() throws Exception {
        mockMvc.perform(get("/books/cart"))
                .andDo(print())
                .andExpect(content().string(containsString("Корзина пуста")))
                .andExpect(status().isOk());
    }
}