package com.example.test.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RsoService {
    private String RIOT_AUTH_URL = "https://auth.riotgames.com";
    public void getRsoInfo(String code) throws Exception {
        try {//Auth코드로 AccessToken과 RefreshToken을 받는 작업.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            //헤더에 데이터 담기 ContentType = application/x-www-form-unencoded;charset

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", "329606cb-fbea-4f9d-859a-b05c10c778a2");
            params.add("client_secret", "nQ5kPGVWsSFqTZY21fbIb4LuUR1ONzr4mOQiKlN61ZY");
            params.add("redirect_uri", "https://findd.findduo.site/oauth/callback");
            params.add("code", code);


            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            //http Entity 룰 생성.

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    RIOT_AUTH_URL + "/token",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            String responseBody = responseEntity.getBody();
            System.out.println(responseBody);
        }
        catch (Exception e){
            System.out.println("SOMETHING WRONG!!"+e.toString());
        }
    }
}
