package com.stockpiece.domain.dtos.Users;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserResponse {
    @NotBlank
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private Double berryBalance;
}
