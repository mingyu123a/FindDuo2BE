package com.example.test.service;

import com.example.test.DTO.TokenDTO;
import com.example.test.DTO.UserRequestDTO;
import com.example.test.entity.UserEntity;
import com.example.test.jwt.TokenProvider;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.test.entity.Authority.ROLE_USER;

@Service
public class LoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KakaoService kakaoService;
    private AuthenticationManagerBuilder managerBuilder;

    // managerBuilder를 초기화하는 생성자 또는 메소드
    public LoginService(AuthenticationManagerBuilder managerBuilder) {
        this.managerBuilder = managerBuilder;
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    public UserEntity NormalSignUp(String id1, String pw1, String nickname1) {
        try{
            UserEntity userEntity = new UserEntity();
            userEntity.setLoginId(id1);
            userEntity.setAuthority(ROLE_USER);
            String encodedPwd = passwordEncoder.encode(pw1);
            System.out.println(encodedPwd);
            userEntity.setPassword(encodedPwd);
            userEntity.setNickname(nickname1);


            return userRepository.save(userEntity);
        }
        catch (Exception e){
            System.out.println("error");
        }
        return null;
    }
    public TokenDTO login(UserRequestDTO requestDto) {
        //인증 요청 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        System.out.println("Auth Token:"+authenticationToken);
        try {
            // AuthenticationManager를 통한 인증 시도, 여기서 비밀번호 검증이 이루어집니다.
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            System.out.println("AUTH: ");

            System.out.println(authentication);
            return tokenProvider.generateTokenDto(authentication);

        } catch (AuthenticationException e) {
            // 여기서 인증 실패 처리
            System.out.println("Authentication failed: " + e.getMessage());

            return null;

        }
    /*
    UsernamePasswordAuthenticationToken은 Spring Security에서 사용자 인증 정보를 나타내는 클래스 중 하나
    사용자의 id와 pwd를 포함하여 사용되는 객체
    UsernamePasswordAuthenticationToken 객체는 인증 과정에서 사용자의 인증 정보를 담는 용도로 사용되고 이걸 구현해야지
    Authentication 인터페이스를 구현ㄱㄴ.
    */
    /*
    AuthenticationManager는 사용자로부터 받은 인증정보(id, pwd)를 기반으로 인증 정보를 수행하는 인터페이스
    구현체는 ProviderManager라는 친구

    Sample 코드
    @Autowired
    private AuthenticationManager authenticationManager;
    public void authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            // 여기서 인증 실패 처리
        }
    }2
    */

    }
}
