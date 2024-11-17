package com.example.test.service;

import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

@Service
public class RsoService {
    private String RIOT_AUTH_URL = "https://auth.riotgames.com";
    private static final String USER_INFO_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/me";
    @Value("${riot.api.key.matchHistoryNumber}")
    private String RIOT_API_KEY;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MatchHistoryService matchHistoryService;
    public String RsoSignUp(String code) throws Exception{
        try {
            String accessToken = getAccessToken(code);
            String[] ids = getPuuidFromAccessToken(accessToken);
            String puuid = ids[0];
            String accountId = ids[1];
            Map<String, String> tierInfo = matchHistoryService.getEncryptedSummonerId(puuid);
            String tier = tierInfo.get("tier");
            String rank = tierInfo.get("rank");
            String[] gamenameTagLine = getGameNameAndTagLineFromPuuid(puuid);
            String gameName = gamenameTagLine[0];
            String tagLine = gamenameTagLine[1];

            UserEntity userEntity = new UserEntity();
            userEntity.setTier(tier + rank);
            userEntity.setNickname(gameName + "#" + tagLine);
            userEntity.setRiotId(gameName + "#" + tagLine);
            userEntity.setLoginId(accountId);
            userEntity.setPassword("1111");
            userRepository.save(userEntity);
            return "성공";
        }catch(Exception e){
            throw new Exception(e);
        }
    }
    public String getAccessToken(String code) throws Exception {
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

            String body = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            String refreshToken = jsonNode.get("refresh_token").asText();
            String scope = jsonNode.get("scope").asText();
            String idToken = jsonNode.get("id_token").asText();
            String tokenType = jsonNode.get("token_type").asText();
            int expiresIn = jsonNode.get("expires_in").asInt();

            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

public String[] getPuuidFromAccessToken(String accessToken) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);  // Bearer 토큰 설정
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            // API 요청 및 응답
            ResponseEntity<String> response = restTemplate.exchange(
                    USER_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            System.out.println(response.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String puuid = jsonNode.get("puuid").asText();
            String accountId = jsonNode.get("accountId").asText();
            String [] ids = new String[2];
            ids[0] = puuid;
            ids[1] = accountId;
            return ids;
            /*
            {"id":"CKqIBRvrg1wljksLt22oa0WD1nAmgjBepjfoaJ1WT-ZrzwRr09IM2JDylw",
            "accountId":"JEdYVx_EfTZEVL0078VKt-XuDPIfgWRJuLcPCKTfUCJZ3fMO1FgcFIJY",
            "puuid":"ZWx1uM_u-RZZF3ycA2jN3g4nt_-NfnNOJZ7E30km_YcjVoMpAi8MmB-q3oF81OcpDpkKq62o_dh5-g",
            "profileIconId":5904,"revisionDate":1731123680000,"summonerLevel":171}
            자동회원가입 구현...
            */
            //이거갖고 밑에 API부르는거 써와야할듯
            //내 티어, gameName, TagLine 을 nickname 에 넣어서 자동 회원가입.



        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public String [] getGameNameAndTagLineFromPuuid(String puuid) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/"+ puuid +"?api_key=" + RIOT_API_KEY,
                HttpMethod.GET,
                entity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        String gameName = jsonNode.get("gameName").asText();
        String tagLine = jsonNode.get("tagLine").asText();
        String [] gameNameTagLines = new String[2];
        gameNameTagLines[0] = gameName;
        gameNameTagLines[1] = tagLine;
        return gameNameTagLines;
    }



}
