package com.subcrii.subcrii.domain.subscription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    @CreationTimestamp
    private LocalDateTime startAt;    // 구독 생성일

    private LocalDateTime lastBillingAt;   // 마지막 결제일

    @NotNull
    private Integer billingDay;  // 매달 청구일 (day)

    private LocalDateTime nextBillingAt;    // 다음 결제 예정일 (존재하지 않으면 그 달 마지막일로 대체)
    private LocalDateTime canceledAt;  // 구독 취소일
}
