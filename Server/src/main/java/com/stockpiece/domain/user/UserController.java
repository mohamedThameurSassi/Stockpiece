package com.stockpiece.domain.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request.getEmail(), request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PostMapping("/{id}/balance")
    public ResponseEntity<User> updateBalance(@PathVariable Integer id, @RequestBody BalanceUpdateRequest request) {
        User user = userService.updateBalance(id, request.getAmount());
        return ResponseEntity.ok(user);
    }
}

@Data
class CreateUserRequest {
    private String email;
    private String name;
}

@Data
class BalanceUpdateRequest {
    private Double amount;
}
