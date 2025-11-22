package com.ucsal.accountservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class GreetingController {

    @Value("${myapp.greeting}")
    private String greeting;

    @GetMapping("/greeting")
    public String greeting() {
        return greeting;
    }
}