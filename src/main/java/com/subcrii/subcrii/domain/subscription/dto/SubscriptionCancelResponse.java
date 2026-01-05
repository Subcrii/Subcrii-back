package com.subcrii.subcrii.domain.subscription.dto;

import com.subcrii.subcrii.domain.subscription.entity.Subscription;
import com.subcrii.subcrii.domain.subscription.entity.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class SubscriptionCancelResponse {
    private UUID subscriptionId;
    private SubscriptionStatus status;
    private LocalDateTime canceledAt;

    public static SubscriptionCancelResponse from(Subscription s) {
        return new SubscriptionCancelResponse(
                s.getId(),
                s.getSubscriptionStatus(),
                s.getCanceledAt()
        );
    }
}
