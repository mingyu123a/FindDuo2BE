package com.example.test.controller;

import com.example.test.DTO.SignUpDTO;
import com.example.test.DTO.TokenDTO;
import com.example.test.DTO.UserRequestDTO;
import com.example.test.entity.UserEntity;
import com.example.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        TokenDTO tokenDTO = loginService.login(requestDto);
        if(tokenDTO !=null){
            return ResponseEntity.ok(tokenDTO);
        }
        else{
            return ResponseEntity.ofNullable(null);
        }
    }
    @PostMapping("/signUp")
    public ResponseEntity<String> response(@RequestBody SignUpDTO dto){
        System.out.println(dto.getId1());
        System.out.println(dto.getPw1());
        System.out.println(dto.getNickname1());
        String message = loginService.NormalSignUp(dto.getId1(), dto.getPw1(),dto.getNickname1(), dto.getTier1(), dto.getRiot_id1());
        if(message.equals("성공")){
            return ResponseEntity.ok("성공");
        }
        else{
            return ResponseEntity.ok(message);
        }
    }
}
