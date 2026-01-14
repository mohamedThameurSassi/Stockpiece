package com.stockpiece.domain.dtos.Users;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBalanceRequest {
    @NotNull(message = "Amount is required")
    private Double amount;
}
