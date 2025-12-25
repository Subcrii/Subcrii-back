package com.subcrii.subcrii.domain.member.controller;

import com.subcrii.subcrii.domain.member.dto.MemberLoginRequest;
import com.subcrii.subcrii.domain.member.dto.MemberResponse;
import com.subcrii.subcrii.domain.member.dto.MemberSignupRequest;
import com.subcrii.subcrii.domain.member.dto.TokenResponse;
import com.subcrii.subcrii.domain.member.entity.Member;
import com.subcrii.subcrii.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "이메일, 비밀번호, 닉네임, 전화번호, 프로필이미지를 입력하여 회원가입합니다."
           )
    public ResponseEntity<MemberResponse> signup(@RequestBody @Valid MemberSignupRequest req) {
        MemberResponse res = memberService.join(req);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/")
                .buildAndExpand(res.getId())
                .toUri();

        return ResponseEntity.created(location).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid MemberLoginRequest req) {
        TokenResponse res = memberService.login(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> getMember(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        return ResponseEntity.ok(memberService.getMemberById(userDetails.getUsername()));
    }
}
