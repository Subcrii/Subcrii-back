package com.subcrii.subcrii.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberLoginRequest {
    @Schema(description = "이메일", example = "user11111@gmail.com", format = "email")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "비밀번호", example = "Password123!")
    @NotBlank
    private String password;
}