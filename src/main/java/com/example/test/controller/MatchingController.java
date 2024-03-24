package com.example.test.controller;

import com.example.test.DTO.FindResponseDTO;
import com.example.test.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
public class MatchingController {

    @Autowired
    private MatchingService service;
    @GetMapping("/team")
    public FindResponseDTO findTeam(@RequestBody int id){
        return service.findTeam(id);
    }
}
