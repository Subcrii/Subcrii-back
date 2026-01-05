package com.subcrii.subcrii.domain.subscription.controller;

import com.subcrii.subcrii.domain.subscription.dto.SubscriptionCancelResponse;
import com.subcrii.subcrii.domain.subscription.dto.SubscriptionCreateResponse;
import com.subcrii.subcrii.domain.subscription.entity.Subscription;
import com.subcrii.subcrii.domain.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creators/{creatorId}/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(summary = "구독하기", description = "유저가 크리에이터를 구독할 수 있습니다. (결제 전 status = PENDING)")
    public ResponseEntity<SubscriptionCreateResponse> subscribe(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID creatorId
    ) {
        UUID memberId = UUID.fromString(userDetails.getUsername());
        return ResponseEntity.ok(subscriptionService.subscribe(memberId, creatorId));
    }

    @DeleteMapping
    @Operation(summary = "구독 취소", description = "유저가 크리에이터 구독을 취소할 수 있습니다. (status = CANCELED)")
    public ResponseEntity<SubscriptionCancelResponse> cancel(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable UUID creatorId
    ) {
        UUID memberId = UUID.fromString(userDetails.getUsername());

        Subscription canceled = subscriptionService.cancel(memberId, creatorId);
        return ResponseEntity.ok(SubscriptionCancelResponse.from(canceled));
    }
}
