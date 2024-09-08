package com.example.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConfigController {

    @Value("${riot.api.key.matchHistoryNumber}")
    private String apiKey;

    @GetMapping("/config")
    public String getConfig() {
        return apiKey;
    }
}
