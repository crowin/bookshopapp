package com.example.bookshopapp.errs;

public class EmptySearchException extends Exception {
    public EmptySearchException(String message) {
        super(message);
    }
}
