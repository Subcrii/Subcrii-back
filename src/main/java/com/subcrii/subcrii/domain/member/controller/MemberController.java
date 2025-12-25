package com.subcrii.subcrii.domain.member.controller;

import com.subcrii.subcrii.domain.member.dto.MemberResponse;
import com.subcrii.subcrii.domain.member.dto.MemberSignupRequest;
import com.subcrii.subcrii.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
                .path("/members/{id}")
                .buildAndExpand(res.getId())
                .toUri();

        return ResponseEntity.created(location).body(res);
    }

}
