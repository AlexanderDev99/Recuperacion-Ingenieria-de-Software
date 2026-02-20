package com.uce.membership.logic.membershipcase;

import com.uce.membership.controllers.data.entities.MembershipDTO;
import com.uce.membership.data.entities.Membership;
import com.uce.membership.data.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import java.util.List;

@Service
public class MembershipUseCaseImpl implements IMembershipUseCase {

    @Autowired
    private MembershipRepository repository;

    @Override
    public MembershipDTO create(MembershipDTO dto) {
        Membership entity = new Membership();
        entity.setUserId(dto.getUserId());
        entity.setPlanType(dto.getPlanType());
        entity.setAmountPaid(dto.getAmountPaid());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setExpirationDate(dto.getExpirationDate());

        Membership saved = repository.save(entity);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public MembershipDTO getById(UUID id) {
        return repository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    public boolean isMembershipActive(String userId) {
        // Comprobar si expirationDate > fecha actual
        return repository.findTopByUserIdOrderByExpirationDateDesc(userId)
                .map(m -> m.getExpirationDate().isAfter(LocalDate.now(ZoneId.of("America/Guayaquil"))))
                .orElse(false);
    }

    private MembershipDTO mapToDTO(Membership entity) {
        MembershipDTO dto = new MembershipDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setPlanType(entity.getPlanType());
        dto.setAmountPaid(entity.getAmountPaid());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setExpirationDate(entity.getExpirationDate());
        return dto;
    }

        @Override
    public List<MembershipDTO> getAll() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }
}