package com.subcrii.subcrii.domain.subscription.repository;

import com.subcrii.subcrii.domain.subscription.entity.Subscription;
import com.subcrii.subcrii.domain.subscription.entity.SubscriptionStatus;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
  boolean existsByMember_IdAndCreator_IdAndSubscriptionStatusIn(
            UUID memberId,
            UUID creatorId,
            Collection<SubscriptionStatus> statuses
    );

    Optional<Subscription> findTopByMember_IdAndCreator_IdAndSubscriptionStatusInOrderByStartAtDesc(
            UUID memberId,
            UUID creatorId,
            Collection<SubscriptionStatus> statuses
    );
  
    long countByCreator_IdAndSubscriptionStatus(UUID creatorId, SubscriptionStatus status);

}
