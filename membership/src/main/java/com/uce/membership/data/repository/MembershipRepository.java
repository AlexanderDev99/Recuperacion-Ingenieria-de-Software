package com.uce.membership.data.repository;

import com.uce.membership.data.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    Optional<Membership> findTopByUserIdOrderByExpirationDateDesc(String userId);

}