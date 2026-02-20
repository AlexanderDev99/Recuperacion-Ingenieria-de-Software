package com.fitflow.scheduler.data.entities.apis.membership;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipApi {
    String userId;
    private PlanType planType;
    private BigDecimal amountPaid;
    private String paymentMethod;
    private LocalDateTime expirationDate;
    
}
