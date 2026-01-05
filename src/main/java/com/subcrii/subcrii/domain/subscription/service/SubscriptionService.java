package com.subcrii.subcrii.domain.subscription.service;

import com.subcrii.subcrii.domain.creator.entity.Creator;
import com.subcrii.subcrii.domain.creator.repository.CreatorRepository;
import com.subcrii.subcrii.domain.member.entity.Member;
import com.subcrii.subcrii.domain.member.repository.MemberRepository;
import com.subcrii.subcrii.domain.subscription.dto.SubscriptionCreateResponse;
import com.subcrii.subcrii.domain.subscription.entity.Subscription;
import com.subcrii.subcrii.domain.subscription.entity.SubscriptionStatus;
import com.subcrii.subcrii.domain.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    @Transactional
    public SubscriptionCreateResponse subscribe(UUID memberId, UUID creatorId) {
        boolean alreadySubscribed = subscriptionRepository.existsByMember_IdAndCreator_IdAndSubscriptionStatusIn(
                memberId,
                creatorId,
                List.of(SubscriptionStatus.PENDING, SubscriptionStatus.ACTIVE)
        );
        if (alreadySubscribed) {
            throw new IllegalStateException("이미 구독중이거나 결제 대기 상태입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다. : " + memberId));

        Creator creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("크리에이터를 찾을 수 없습니다.: " + creatorId));

        Subscription subscription = subscriptionRepository.save(
                Subscription.createSubscription(creator, member, LocalDateTime.now())
        );
        return SubscriptionCreateResponse.from(subscription);
    }

    @Transactional
    public Subscription cancel(UUID memberId, UUID creatorId) {
        Subscription subscription = subscriptionRepository
                .findTopByMember_IdAndCreator_IdAndSubscriptionStatusInOrderByStartAtDesc(
                        memberId,
                        creatorId,
                        List.of(SubscriptionStatus.PENDING, SubscriptionStatus.ACTIVE)
                )
                .orElseThrow(() -> new IllegalArgumentException("구독 정보를 찾을 수 없습니다."));

        subscription.cancel(LocalDateTime.now());
        return subscription;
    }

}
