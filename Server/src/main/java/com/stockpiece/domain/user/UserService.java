package com.stockpiece.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public User createUser(String email, String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .berryBalance(10000.0)
                .build();
        return userRepository.save(user);
    }
    
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateBalance(Integer userId, Double amount) {
        User user = getUserById(userId);
        user.setBerryBalance(user.getBerryBalance() + amount);
        return userRepository.save(user);
    }
}
