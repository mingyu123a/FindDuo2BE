package com.example.test.controller;

import com.example.test.service.RsoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class RsoController {
    @Autowired
    private RsoService rsoService;
    @GetMapping("callback")
    public String getRsoAuthCode(HttpServletRequest request)throws Exception{//httpServletRequest객체로 uil내의 파라미터,. path 등에 접근 가능
        String authCode = request.getParameter("code");//getParameter 메서드를 통해 auth 코드 가져옴
        System.out.println("Authrization code:"+authCode);
        String msg = rsoService.RsoSignUp(authCode);
        if(msg == "성공"){
           return "redirect:https://findd.findduo.site";
        }
        return "실패";
    }
}
