package com.subcrii.subcrii.domain.subscription.entity;

public enum SubscriptionStatus {
    ACTIVE,     // 정상 구독
    CANCELED,   // 취소됨 (남은 기간 유지)
    EXPIRED     // 구독 종료 (기간 끝남)
}
