package com.subcrii.subcrii.domain.member.dto;

import com.subcrii.subcrii.domain.member.entity.Member;
import java.util.UUID;
import lombok.Getter;

@Getter
public class MemberResponse {
    UUID id;
    private String email;
    private String name;
    private String phoneNumber;
    private String profileImage;

    public static MemberResponse create(Member newMember) {
        return new MemberResponse(newMember);
    }
    private MemberResponse(Member newMember) {
        this.id = newMember.getId();
        this.email = newMember.getEmail();
        this.name = newMember.getName();
        this.phoneNumber = newMember.getPhoneNumber();
        this.profileImage = newMember.getProfileImage();


    }
}
