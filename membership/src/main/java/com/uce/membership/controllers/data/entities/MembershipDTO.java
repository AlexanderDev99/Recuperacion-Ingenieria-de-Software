package com.uce.membership.controllers.data.entities;

import com.uce.membership.data.entities.PlanType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class MembershipDTO {
    private UUID id; // Para que el GET devuelva el ID

    @NotBlank(message = "El ID de usuario es obligatorio")
    private String userId;

    @NotNull(message = "El tipo de plan es obligatorio")
    private PlanType planType;

    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal amountPaid;

    @NotBlank(message = "El método de pago es requerido")
    private String paymentMethod;

    @NotNull(message = "La fecha de expiración es requerida")
    private LocalDate expirationDate;
}