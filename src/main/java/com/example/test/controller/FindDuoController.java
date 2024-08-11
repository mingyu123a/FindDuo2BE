package com.example.test.controller;

import com.example.test.service.FindDuoService;
import com.example.test.service.MatchHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/duo")
public class FindDuoController {

    private final FindDuoService findDuoService;
    private MatchHistoryService matchHistoryService;

    @Autowired
    public FindDuoController(FindDuoService findDuoService) {
        this.findDuoService = findDuoService;
    }

    @GetMapping
    public String findDuo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Logged in username: " + username);

        // 이메일 서비스로 보내고, 서비스에서 Riot ID 찾기
        findDuoService.findRiotIdByEmail(username);

        // 여기서는 일단 null을 반환하지 않고, 실제로 필요한 값을 반환하도록 수정 필요
        return "Operation Complete"; // 예시 반환 값
    }
}
