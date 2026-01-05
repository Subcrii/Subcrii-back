package com.subcrii.subcrii.domain.subscription.entity;

import com.subcrii.subcrii.domain.creator.entity.Creator;
import com.subcrii.subcrii.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
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

    private Subscription(Creator creator, Member member, SubscriptionStatus subscriptionStatus,
                         Integer billingDay, LocalDateTime nextBillingAt) {
        if (creator == null || member == null) {
            throw new IllegalArgumentException("creator/member는 필수");
        }
        this.creator = creator;
        this.member = member;
        this.subscriptionStatus = subscriptionStatus;
        this.billingDay = billingDay;
        this.nextBillingAt = nextBillingAt;
    }

    public static Subscription createSubscription(Creator creator, Member member, LocalDateTime now) {
        if (now == null) now = LocalDateTime.now();

        int billingDay = now.getDayOfMonth();
        LocalDateTime nextBillingAt = computeNextBillingAt(now, billingDay);
        return new Subscription(creator, member, SubscriptionStatus.PENDING, billingDay, nextBillingAt);
    }

    private static LocalDateTime computeNextBillingAt(LocalDateTime base, int billingDay) {
        LocalDate baseDate = base.toLocalDate();
        LocalDate nextMonth = baseDate.plusMonths(1).withDayOfMonth(1);

        if (billingDay < 1 || billingDay > 31) {
            throw new IllegalArgumentException("매월 청구일은 1~31일 사이여야 합니다.");
        }

        int lastDay = YearMonth.from(nextMonth).lengthOfMonth();
        int day = Math.min(billingDay, lastDay);

        return nextMonth.withDayOfMonth(day).atTime(base.toLocalTime());
    }

    public void cancel(LocalDateTime now) {
        if (this.subscriptionStatus == SubscriptionStatus.CANCELED) return;
        this.subscriptionStatus = SubscriptionStatus.CANCELED;
        this.canceledAt = (now != null) ? now : LocalDateTime.now();
    }
}
