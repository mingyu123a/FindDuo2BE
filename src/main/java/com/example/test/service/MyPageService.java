package com.example.test.service;

import com.example.test.DTO.MyPageDTO;
import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Optional;
@Service
public class MyPageService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchHistoryService matchHistoryService;


    public MyPageDTO userInfo(String email) throws JsonProcessingException, InterruptedException {
        System.out.println("마이페이지 조회");
        System.out.println("접근유저 email: "+email);
        Optional<UserEntity> userEntityOptional = userRepository.findByLoginId(email);
        UserEntity userEntity = userEntityOptional.orElse(null);
        MyPageDTO myPageDTO = new MyPageDTO();
        myPageDTO.setRiotId(userEntity.getRiotId());
        myPageDTO.setEmail(userEntity.getLoginId());
        myPageDTO.setTier(userEntity.getTier());
        String [] riotId = userEntity.getRiotId().split("#");
        String gameName = riotId[0];
        String tagLine = riotId[1];
        System.out.println("gameName: " +gameName);
        System.out.println("tagLine: " +tagLine);
        String puuid = matchHistoryService.getPuuid(gameName,tagLine);
        System.out.println(puuid);
        Map<String,String> EncryptedSummonerId = matchHistoryService.getEncryptedSummonerId(puuid);
        String [][] championInfoResult = matchHistoryService.getChampMateryTop(puuid);

        System.out.println(EncryptedSummonerId);
        String tier = EncryptedSummonerId.get("tier");
        String rank = EncryptedSummonerId.get("rank");
        String leaguePoints = EncryptedSummonerId.get("leaguePoints");
        String wins = EncryptedSummonerId.get("wins");
        String losses = EncryptedSummonerId.get("losses");
        myPageDTO.setTier(tier);
        myPageDTO.setRank(rank);
        myPageDTO.setLeaguePoints(leaguePoints);
        myPageDTO.setWins(wins);
        myPageDTO.setLosses(losses);
        myPageDTO.setChampionId(championInfoResult[0]);
        myPageDTO.setChampionPoints(championInfoResult[1]);
        //tierResponse에 있는것 다 넣으면 좋을듯.
        return myPageDTO;
    }
}
