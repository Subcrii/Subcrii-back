package com.subcrii.subcrii.domain.subscription.repository;

import com.subcrii.subcrii.domain.subscription.entity.Subscription;
import com.subcrii.subcrii.domain.subscription.entity.SubscriptionStatus;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    long countByCreator_IdAndSubscriptionStatus(UUID creatorId, SubscriptionStatus status);
}
