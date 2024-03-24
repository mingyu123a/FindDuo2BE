package com.example.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String loginId;
    private String password;
    private Authority authority;



    private int tier;
    private String riot_id;
    private String nickname;
    private String nickname1;
    private String refreshToken;



    @OneToMany
    @JoinColumn(name = "community")
    private List<CommunityEntity> community = new ArrayList<>();
//
    @OneToMany
    @JoinColumn(name = "champion_analysis")
    private List<ChampionAnalysisEntity> championAnalysis = new ArrayList<>();

    @Builder
    public UserEntity(){
    }

}

