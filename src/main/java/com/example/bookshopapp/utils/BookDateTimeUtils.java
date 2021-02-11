package com.example.bookshopapp.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class BookDateTimeUtils {

    public Date parseDate(String stringDate) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(stringDate);
    }
}
