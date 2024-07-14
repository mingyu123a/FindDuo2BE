package com.example.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class MatchHistoryService {

    @Value("${riot.api.key.matchHistoryNumber}")
    private String RIOT_API_KEY;
    private String RIOT_URL_ASIA = "https://asia.api.riotgames.com";
    private String RIOT_URL_KR = "https://kr.api.riotgames.com";

    public String getPuuid(String gameName, String tagLine) throws JsonProcessingException, InterruptedException {
        String a = gameName + "/" + tagLine;
        HttpHeaders headers = new HttpHeaders();
        //HttpHeader 담기, 위의 과정과 동일
        RestTemplate restTem = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTem.exchange(
                RIOT_URL_ASIA + "/riot/account/v1/accounts/by-riot-id/" + a + "?api_key=" + RIOT_API_KEY,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        System.out.println(response);

        String responseBody = response.getBody();
        System.out.println(responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String puuid = jsonNode.get("puuid").asText();
        System.out.println("puuid: " + puuid);
        return puuid;
    }

    public String [] getMatchHistoryNumber(String puuid) throws JsonProcessingException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTem = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTem.exchange(
                RIOT_URL_ASIA + "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20&api_key=" + RIOT_API_KEY,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        System.out.println(response.getStatusCode());
        String responseBody = response.getBody();
        System.out.println(responseBody);
        String[] matchList = new String[10];
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        for (int i = 0; i < 10; i++) {
            matchList[i] = jsonNode.get(i).asText();
        }
        return matchList;
    }


    public Map<String, String> getEncryptedSummonerId(String puuid) throws JsonProcessingException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTem = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTem.exchange(
                RIOT_URL_KR + "/lol/summoner/v4/summoners/by-puuid/" + puuid + "?api_key=" + RIOT_API_KEY,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        System.out.println(response.getStatusCode());
        String responseBody = response.getBody();
        System.out.println(responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String encryptedSummonerId =  jsonNode.get("id").asText();
        System.out.println(encryptedSummonerId);
        return getRankGameTier(encryptedSummonerId);

    }
    public Map<String,String> getRankGameTier(String encryptedSummonerId) throws JsonProcessingException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                RIOT_URL_KR + "/lol/league/v4/entries/by-summoner/" + encryptedSummonerId + "?api_key=" + RIOT_API_KEY,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        System.out.println(response.getStatusCode());
        String responseBody = response.getBody();
        System.out.println(responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode2 = objectMapper.readTree(responseBody);

        // Check if jsonNode2 is null or empty
        if (jsonNode2 == null || !jsonNode2.isArray() || jsonNode2.size() == 0) {
            Map<String,String> tierResponse = new HashMap<>();
            tierResponse.put("tier", "언랭");
            tierResponse.put("rank", "");
            tierResponse.put("leaguePoints", "0");
            tierResponse.put("wins", "0");
            tierResponse.put("losses", "0");
            return tierResponse;
        }

        JsonNode jsonNode = jsonNode2.get(0);

        // Check if jsonNode is null
        if (jsonNode == null) {
            throw new IllegalArgumentException("JSON node is null");
        }

        // Safely retrieve fields from jsonNode
        String tier = jsonNode.has("tier") ? jsonNode.get("tier").asText() : "";
        String rank = jsonNode.has("rank") ? jsonNode.get("rank").asText() : "";
        String leaguePoints = jsonNode.has("leaguePoints") ? jsonNode.get("leaguePoints").asText() : "";
        String wins = jsonNode.has("wins") ? jsonNode.get("wins").asText() : "";
        String losses = jsonNode.has("losses") ? jsonNode.get("losses").asText() : "";

        Map<String,String> tierResponse = new HashMap<>();
        tierResponse.put("tier", tier);
        tierResponse.put("rank", rank);
        tierResponse.put("leaguePoints", leaguePoints);
        tierResponse.put("wins", wins);
        tierResponse.put("losses", losses);

        return tierResponse;
    }
    public String[] getPuuidBySummonerId(String []encryptedSummonerId) throws JsonProcessingException {
        String [] gameNameTagLine = new String[10];
        for (int i = 0; i < 10 ; i++) {
            HttpHeaders headers = new HttpHeaders();
            RestTemplate restTem = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTem.exchange(
                    RIOT_URL_KR + "/lol/summoner/v4/summoners/" + encryptedSummonerId[i] + "?api_key=" + RIOT_API_KEY,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );
            System.out.println(response);
            //여기서 puuid 뽑아서 다시 String배열에 저장
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String puuid = jsonNode.get("puuid").asText();
            System.out.println(puuid);
            HttpHeaders headers2 = new HttpHeaders();
            RestTemplate restTem2 = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity2 = new HttpEntity<>(headers);
            ResponseEntity<String> response2 = restTem.exchange(
                    RIOT_URL_ASIA + "/riot/account/v1/accounts/by-puuid/" + puuid + "?api_key=" + RIOT_API_KEY,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            );
            String responseBody2 = response2.getBody();
            System.out.println(responseBody2);
            ObjectMapper objectMapper2 = new ObjectMapper();
            JsonNode jsonNode2 = objectMapper2.readTree(responseBody2);
            String gameName = jsonNode2.get("gameName").asText();
            String TagLine = jsonNode2.get("tagLine").asText();
            System.out.println(gameName+TagLine);
            gameNameTagLine[i] = gameName + "#"+TagLine;

        }
        return gameNameTagLine;

    }
        //String 배열을 받아서 새로운 함수 하나 작성 puuid -> gameName + TagLine받는거

    /*
    public void getMatchHistoryInform(String[] matchList) throws JsonProcessingException, InterruptedException {
        Thread.sleep(3000);
        System.out.println("getMatchHistoryInform");
        String url = RIOT_URL_ASIA + "/lol/match/v5/matches/" + matchList[0] + "?api_key=" + RIOT_API_KEY;
        System.out.println(url);
        RestTemplate restTem = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null);
        ResponseEntity<String> response = restTem.exchange(
                RIOT_URL_ASIA + "/lol/match/v5/matches/" + matchList[0] + "?api_key=" + RIOT_API_KEY,
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        HttpStatusCode code = response.getStatusCode();
        System.out.println(code);
        for (int i = 0; i < 1; i++) {

        }
    }*/

}