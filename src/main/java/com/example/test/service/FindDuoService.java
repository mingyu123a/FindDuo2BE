package com.example.test.service;

import com.example.test.entity.UserEntity;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindDuoService {

    private final UserRepository userRepository;

    @Autowired
    public FindDuoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findRiotIdByEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findByLoginId(email);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String riotId = user.getRiotId();
            System.out.println("Found Riot ID: " + riotId);

        } else {
            System.out.println("User not found for email: " + email);
        }
    }
}
