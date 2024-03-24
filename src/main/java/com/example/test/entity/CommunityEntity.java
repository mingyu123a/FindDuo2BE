package com.example.test.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
public class CommunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nullable
    private String title;
    private String champion;

    private Integer tier;

}
