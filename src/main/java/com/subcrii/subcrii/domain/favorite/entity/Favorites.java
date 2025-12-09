package com.subcrii.subcrii.domain.favorite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID memberId;

    @Column(nullable = false)
    private UUID creatorId;

    @CreationTimestamp
    private LocalDateTime createdAt;


    private Favorites(UUID memberId, UUID creatorId) {
        if (memberId == null || creatorId == null) {
            throw new IllegalArgumentException("memberId / creatorId는 필수");
        }
        this.memberId = memberId;
        this.creatorId = creatorId;
    }

    public static Favorites create(UUID memberId, UUID creatorId) {
        return new Favorites(memberId, creatorId);
    }
}
