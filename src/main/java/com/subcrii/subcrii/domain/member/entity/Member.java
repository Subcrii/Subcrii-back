package com.subcrii.subcrii.domain.member.entity;

import com.subcrii.subcrii.domain.member.dto.MemberSignupRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_member_email", columnNames = "email")
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false, length = 100)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String phoneNumber;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public static Member create(MemberSignupRequest req, String encodedPassword) {
        return new Member(req, encodedPassword);
    }

    private Member(MemberSignupRequest req, String encodedPassword){
        this.email = req.getEmail();
        this.password = encodedPassword;
        this.name = req.getName();
        this.phoneNumber = req.getPhoneNumber();
        this.profileImage = (req.getProfileImage() == null) ? "" : req.getProfileImage();
        this.role = Role.USER;
    }

    public void changeRole(Role role) {
        this.role = role;
    }
}