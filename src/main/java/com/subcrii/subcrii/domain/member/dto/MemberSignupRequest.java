package com.subcrii.subcrii.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class MemberSignupRequest {

    @Schema(description = "이메일", example = "user11111@gmail.com", format = "email")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "비밀번호", example = "Password123!")
    @NotBlank
    private String password;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    private String name;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    @NotBlank
    @Pattern(
            regexp = "^(0\\d{1,2}|01[016789])-?\\d{3,4}-?\\d{4}$",
            message = "phoneNumber 형식이 올바르지 않습니다. 예) 010-1234-5678"
    )
    private String phoneNumber;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
    @URL
    private String profileImage;
}
