package com.example.bookshopapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BooksControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public BooksControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void bookPage() throws Exception {
        mockMvc.perform(get("/books/book-apv-767"))
                .andDo(print())
                .andExpect(content().string(containsString("Goddess, The (Shen nu)")))
                .andExpect(content().string(containsString("Arri Tricker")))
                .andExpect(content().string(containsString("Nullam orci pede")))
                .andExpect(status().isOk());
    }
}