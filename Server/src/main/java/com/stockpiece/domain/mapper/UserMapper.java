package com.stockpiece.domain.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.stockpiece.domain.dtos.Users.UserResponse;
import com.stockpiece.domain.model.User;
import java.util.List;

@Component
public class UserMapper {
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .berryBalance(user.getBerryBalance())
                .build();
    }
    
    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
