package com.hg.dashboard.service;

import com.hg.dashboard.domain.User;
import com.hg.dashboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}

