package com.example.test.DTO;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindResponseDTO {

    private String id;
    private int tier;

}
