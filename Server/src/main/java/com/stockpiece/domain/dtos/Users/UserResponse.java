package com.stockpiece.domain.dtos.Users;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    @NotBlank
    private UUID id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private Double berryBalance;
}
