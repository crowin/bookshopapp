package com.example.bookshopapp.errs;

public class BadUserAuthorization extends Exception {

    public BadUserAuthorization(String errorMsg) {
        super(errorMsg);
    }
}
