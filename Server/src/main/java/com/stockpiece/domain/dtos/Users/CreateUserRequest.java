package com.stockpiece.domain.dtos.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "User name cannot be blank")
    @Size(min = 3, max = 50, message = "User name must be between 3 and 50 characters")
    private String userName;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

        @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace"
    )
    private String password;
}
