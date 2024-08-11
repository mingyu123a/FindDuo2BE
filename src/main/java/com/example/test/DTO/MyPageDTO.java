package com.example.test.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyPageDTO {
    private String wins;
    private String losses;
    private String tier;
    private String rank;
    private String leaguePoints;
    private String email;
    private String riotId;
    private String [] championId;
    private String [] championPoints;

}
