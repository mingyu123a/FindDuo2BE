package com.example.test.controller;

import com.example.test.DTO.SignUpDTO;
import com.example.test.DTO.TokenDTO;
import com.example.test.DTO.UserRequestDTO;
import com.example.test.entity.UserEntity;
import com.example.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class NormalLoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserRequestDTO requestDto) {
        return ResponseEntity.ok(loginService.login(requestDto));
    }
    @PostMapping("/signUp")
    public ResponseEntity<UserEntity> response(@RequestBody SignUpDTO dto){
        System.out.println(dto.getId1());
        System.out.println(dto.getPw1());
        System.out.println(dto.getNickname1());
        return ResponseEntity.ok(loginService.NormalSignUp(dto.getId1(), dto.getPw1(),dto.getNickname1()));
    }
}
