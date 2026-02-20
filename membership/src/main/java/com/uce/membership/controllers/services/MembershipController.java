package com.uce.membership.controllers.services;

import com.uce.membership.controllers.data.entities.MembershipDTO;
import com.uce.membership.data.entities.PlanType;
import com.uce.membership.logic.membershipcase.IMembershipUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/memberships") // Base del API según ambos PDFs
public class MembershipController {

    @Autowired
    private IMembershipUseCase useCase;
    /**
     * GET /api/v1/memberships/create
     * Crear membresía usando parámetros de URL (para pruebas desde navegador)
     */
    //lo que sea
    @GetMapping("/create")
    public ResponseEntity<MembershipDTO> createFromUrl(
            @RequestParam String userId,
            @RequestParam PlanType planType,
            @RequestParam BigDecimal amountPaid,
            @RequestParam String paymentMethod,
            @RequestParam String expirationDate) {

        MembershipDTO dto = new MembershipDTO();
        dto.setUserId(userId);
        dto.setPlanType(planType);
        dto.setAmountPaid(amountPaid);
        dto.setPaymentMethod(paymentMethod);
        dto.setExpirationDate(LocalDate.parse(expirationDate));

        MembershipDTO created = useCase.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/memberships/{id}
     * Requerimiento: Obtener detalle por ID (PDF 2)
     */
    @GetMapping("/{id}")
    public ResponseEntity<MembershipDTO> getById(@PathVariable UUID id) {
        MembershipDTO membership = useCase.getById(id);
        return membership != null
                ? ResponseEntity.ok(membership)
                : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/v1/memberships/active/{userId}
     * Requerimiento: Validar si la membresía está activa (fecha caducidad > hoy)
     * Este endpoint será usado por el Scheduler Service (PDF 2 - Fase 2).
     */
    @GetMapping("/active/{userId}")
    public ResponseEntity<Boolean> isActive(@PathVariable String userId) {
        boolean active = useCase.isMembershipActive(userId);
        return ResponseEntity.ok(active);
    }
}
