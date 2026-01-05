package com.subcrii.subcrii.domain.subscription.dto;

import com.subcrii.subcrii.domain.subscription.entity.Subscription;
import com.subcrii.subcrii.domain.subscription.entity.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class SubscriptionCreateResponse {
    private UUID subscriptionId;
    private SubscriptionStatus status;
    private LocalDateTime nextBillingAt;

    public static SubscriptionCreateResponse from(Subscription s) {
        return new SubscriptionCreateResponse(
                s.getId(),
                s.getSubscriptionStatus(),
                s.getNextBillingAt()
        );
    }
}
