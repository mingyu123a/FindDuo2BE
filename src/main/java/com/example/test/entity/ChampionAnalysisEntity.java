package com.example.test.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
public class ChampionAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nullable
    private int season;
    private int win;
    private int lose;
//    private double kill;
//    private double death;
//    private double assist;
//    private double cs;
//    private int gold;
//    private int doublekill;
//    private int triplekill;
//    private int quadrakill;
//    private int pentakill;

}
