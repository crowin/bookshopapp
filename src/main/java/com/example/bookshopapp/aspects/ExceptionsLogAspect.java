package com.example.bookshopapp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionsLogAspect {

    @Pointcut("within(com.example.bookshopapp.controllers..*Controller)")
    private void anyController() {}


    @AfterThrowing(pointcut = "anyController()", throwing = "ex")
    private void afterControllerException(JoinPoint jp, Exception ex) {
        log.error("Exception in {} controller: {}", jp.getTarget().getClass().getSimpleName(), ex.getMessage());

    }
}
