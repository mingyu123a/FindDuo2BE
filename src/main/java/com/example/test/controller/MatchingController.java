package com.example.test.controller;

import com.example.test.DTO.FindResponseDTO;
import com.example.test.DTO.MyPageDTO;
import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import com.example.test.service.MatchingService;
import com.example.test.service.MyPageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
public class MatchingController {

    @Autowired
    private MatchingService service;
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/team")
    public FindResponseDTO findTeam(@RequestBody int id){
        return service.findTeam(id);
    }
    @GetMapping("/findduo")
    public MyPageDTO findduo() throws JsonProcessingException, InterruptedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(email);
        UserEntity userEntity = userRepository.findByLoginId(email).orElseThrow();
        String tier = userEntity.getTier();
        UserEntity sameTierUser = userRepository.findFirstByTierAndLoginIdNot(tier,email);
        System.out.println(sameTierUser.getLoginId());
        String email2 = sameTierUser.getLoginId();
        MyPageDTO myPageDTO = myPageService.userInfo(email2);
        return myPageDTO;

    }
}
