package com.example.bookshopapp.aspects;

import com.example.bookshopapp.data.Book;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Aspect
@Component
@Slf4j
public class BooksLogAspect {

    @AfterReturning(pointcut = "@annotation(com.example.bookshopapp.aspects.annotations.LogCount)", returning = "bookList")
    public void logExecutionTime(JoinPoint joinPoint, List<Book> bookList) throws Throwable {
        String uri = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        String searchBook = joinPoint.getArgs()[0].toString();

        log.info("{} for '{}' returned {} books", uri, searchBook, bookList.size());
    }
}
