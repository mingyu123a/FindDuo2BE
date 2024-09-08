package com.example.test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riot.txt")
public class riotAuthenticationController {
    @GetMapping
    public String test(){
        return "7b702273-faa3-4cb1-98c9-084f7bc01fcd";
    }
}
