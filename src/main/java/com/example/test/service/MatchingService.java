package com.example.test.service;

import com.example.test.DTO.FindResponseDTO;
import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {
    @Autowired
    private UserRepository userRepository;

    public FindResponseDTO findTeam(int id){
        UserEntity entity = userRepository.findById(id);
        FindResponseDTO dto = new FindResponseDTO();
        dto.setId(entity.getRiot_id());
        dto.setTier(entity.getTier());
        return dto;
    }
}
