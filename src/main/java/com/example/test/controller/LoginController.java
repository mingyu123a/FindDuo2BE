package com.example.test.controller;

import com.example.test.DTO.DTO;
import com.example.test.DTO.FindResponseDTO;
import com.example.test.DTO.SignUpDTO;
import com.example.test.entity.UserEntity;
import com.example.test.service.KakaoService;
import com.example.test.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/callback") //callback으로 들어온 요청을 받겠다.
public class LoginController{

    @Autowired//Kakao Service 정의 및 의존관계 자동 설정
    private KakaoService kakaoService;

    @Autowired
    private LoginService loginService;
    @GetMapping("") //callback으로 들어온 GET요청
    public void getKakaoAuthCode(HttpServletRequest request)throws Exception{//httpServletRequest객체로 uil내의 파라미터,. path 등에 접근 가능
        String authCode = request.getParameter("code");//getParameter 메서드를 통해 auth 코드 가져옴
        System.out.println("Authrization code:"+authCode);
        kakaoService.getKakaoInfo(authCode); //getKakaoInfo로 Auth코드 전송
    }

}
