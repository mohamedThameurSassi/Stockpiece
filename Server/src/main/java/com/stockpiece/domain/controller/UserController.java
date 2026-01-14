package com.stockpiece.domain.controller;
import com.stockpiece.domain.dtos.Users.CreateUserRequest;
import com.stockpiece.domain.dtos.Users.UserResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stockpiece.domain.dtos.Users.UpdateBalanceRequest;
import com.stockpiece.domain.model.User;
import com.stockpiece.domain.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PostMapping("/{id}/balance")
    public ResponseEntity<UserResponse> updateBalance(@PathVariable Integer id, @RequestBody UpdateBalanceRequest request) {
        UserResponse user = userService.updateBalance( id, request);
        return ResponseEntity.ok(user);
    }
}


