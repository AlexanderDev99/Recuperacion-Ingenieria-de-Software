package com.uce.membership.logic.membershipcase;

import com.uce.membership.controllers.data.entities.MembershipDTO;
import java.util.UUID;
import java.util.List;

public interface IMembershipUseCase {
    
    MembershipDTO create(MembershipDTO dto);

    MembershipDTO getById(UUID id);

    boolean isMembershipActive(String userId);

    List<MembershipDTO> getAll();
}