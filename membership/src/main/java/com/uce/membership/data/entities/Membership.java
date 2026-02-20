package com.uce.membership.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "memberships")
@Data
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Cambiado a UUID según el plan
    private UUID id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private PlanType planType;

    private BigDecimal amountPaid;
    private String paymentMethod;
    private LocalDate expirationDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Sugerido en el modelo mínimo
}