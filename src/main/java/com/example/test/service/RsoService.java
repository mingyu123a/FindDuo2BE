package com.example.test.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.JsonObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RsoService {
    private String RIOT_AUTH_URL = "https://auth.riotgames.com";
    private static final String USER_INFO_URL = "https://auth.riotgames.com/riot/account/v1/accounts/me";

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
            //accessToken 뽑아
            String accessToken ="";
            // 헤더 설정
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setBearerAuth(accessToken);  // Bearer 토큰 설정
            headers2.setContentType(MediaType.APPLICATION_JSON);

            // HttpEntity 생성 (요청 헤더 포함)
            HttpEntity<String> entity = new HttpEntity<>(headers2);

            try {
                // API 요청 및 응답
                ResponseEntity<String> response = restTemplate.exchange(
                        USER_INFO_URL,
                        HttpMethod.GET,
                        entity,
                        String.class
                );
                System.out.println(response.getBody());
                String body = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(body);


                // 각 요소 추출
                accessToken = jsonNode.get("access_token").asText();
                System.out.println("ACCESSTOKEN: "+accessToken);
                String refreshToken = jsonNode.get("refresh_token").asText();
                String scope = jsonNode.get("scope").asText();
                String idToken = jsonNode.get("id_token").asText();
                String tokenType = jsonNode.get("token_type").asText();
                int expiresIn = jsonNode.get("expires_in").asInt();

                HttpHeaders getUserInfoHeaders = new HttpHeaders();
                getUserInfoHeaders.setBearerAuth(accessToken);  // "Authorization" 헤더에 "Bearer <accessToken>" 형식으로 설정됨
                HttpEntity<String> getUserInfoEntity = new HttpEntity<>(headers);


                ResponseEntity<String> userInfoResponse = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, getUserInfoEntity, String.class);
                System.out.println("User Info Response: " + userInfoResponse.getBody());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            System.out.println("SOMETHING WRONG!!"+e.toString());
        }
    }
}
