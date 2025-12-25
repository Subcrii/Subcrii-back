package com.subcrii.subcrii.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String tokenType;
    private String accessToken;
    private long expiresIn;
}
