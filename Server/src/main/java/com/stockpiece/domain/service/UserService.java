package com.stockpiece.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockpiece.domain.mapper.UserMapper;
import com.stockpiece.domain.dtos.Users.CreateUserRequest;
import com.stockpiece.domain.dtos.Users.UpdateBalanceRequest;
import com.stockpiece.domain.dtos.Users.UserResponse;
import com.stockpiece.domain.model.User;
import com.stockpiece.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .berryBalance(10000.0)
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
    
    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseList(users);
    }
    
    @Transactional
    public UserResponse updateBalance(Integer user_id, UpdateBalanceRequest request) {
        if (user_id == null) {
            throw new RuntimeException("User ID cannot be null");
        }
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Double newBalance = user.getBerryBalance() + request.getAmount();
        if (newBalance < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        user.setBerryBalance(newBalance);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }
}
