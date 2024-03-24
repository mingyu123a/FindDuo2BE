package com.example.test.controller;

import com.example.test.service.MatchHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/history")
public class MatchHistoryController {

    @Autowired
    private MatchHistoryService matchHistoryService;
    @GetMapping("/MatchHistory")
    public ResponseEntity<String[]> abc(@RequestParam("gameName") String gameName, @RequestParam("tagLine") String tagLine) throws JsonProcessingException, InterruptedException {
        System.out.println(gameName);
        String puuid = matchHistoryService.getPuuid(gameName,tagLine);
        String [] matchList = matchHistoryService.getMatchHistoryNumber(puuid);
        System.out.println(ResponseEntity.ok(matchList));
        return ResponseEntity.ok(matchList);
    }
    @GetMapping("/rankGameTier")
    public ResponseEntity<Map<String,String>> getTier(@RequestParam("gameName") String gameName, @RequestParam("tagLine") String tagLine) throws JsonProcessingException, InterruptedException {
        String puuid = matchHistoryService.getPuuid(gameName,tagLine);
        Map<String,String> tier = new HashMap<>(matchHistoryService.getEncryptedSummonerId(puuid));
        return ResponseEntity.ok(tier);
    }

}
