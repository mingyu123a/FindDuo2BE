package com.example.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    @Builder
    public TestEntity(){
    }

    public String getTitle(){return title;}
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent() {return content;}
    public void setContent(String content){
        this.content = content;
    }

}
