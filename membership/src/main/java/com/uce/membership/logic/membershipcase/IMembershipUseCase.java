package com.uce.membership.logic.membershipcase;

import com.uce.membership.controllers.data.entities.MembershipDTO;
import java.util.UUID;

public interface IMembershipUseCase {
    MembershipDTO create(MembershipDTO dto);

    MembershipDTO getById(UUID id);

    boolean isMembershipActive(String userId); // Requerimiento nuevo del plan
}