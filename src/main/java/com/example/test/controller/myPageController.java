package com.example.test.controller;

import com.example.test.DTO.MyPageDTO;
import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import com.example.test.service.MyPageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/mypage")
public class myPageController {

    @Autowired
    private MyPageService myPageService;
    @GetMapping("/mypage")
    public MyPageDTO getMypage() throws JsonProcessingException, InterruptedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        MyPageDTO myPageDTO = myPageService.userInfo(email);
        return myPageDTO;
    }
}
