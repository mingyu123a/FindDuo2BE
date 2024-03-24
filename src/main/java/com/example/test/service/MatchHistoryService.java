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
        String[] matchList = new String[20];
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        for (int i = 0; i < 20; i++) {
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
        RestTemplate restTem = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTem.exchange(
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
        JsonNode jsonNode = jsonNode2.get(0);
        String tier =  jsonNode.get("tier").asText();
        String rank =  jsonNode.get("rank").asText();
        String summonerName =  jsonNode.get("summonerName").asText();
        String leaguePoints =  jsonNode.get("leaguePoints").asText();
        String wins =  jsonNode.get("wins").asText();
        String losses =  jsonNode.get("losses").asText();
        Map<String,String> tierResponse = new HashMap<>();
        tierResponse.put("tier", tier);
        tierResponse.put("rank", rank);
        tierResponse.put("summonerName", summonerName);
        tierResponse.put("leaguPoints",leaguePoints);
        tierResponse.put("wins", wins);
        tierResponse.put("losses",losses);
        return tierResponse;

    }
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

