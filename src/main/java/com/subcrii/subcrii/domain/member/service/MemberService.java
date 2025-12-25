package com.subcrii.subcrii.domain.member.service;

import com.subcrii.subcrii.domain.member.dto.MemberLoginRequest;
import com.subcrii.subcrii.domain.member.dto.MemberResponse;
import com.subcrii.subcrii.domain.member.dto.MemberSignupRequest;
import com.subcrii.subcrii.domain.member.dto.TokenResponse;
import com.subcrii.subcrii.domain.member.entity.Member;
import com.subcrii.subcrii.domain.member.repository.MemberRepository;
import com.subcrii.subcrii.global.auth.TokenProvider;
import com.subcrii.subcrii.global.exception.EmailDuplicatedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public MemberResponse join(MemberSignupRequest memberSignupRequest) {
        if (memberRepository.existsByEmail(memberSignupRequest.getEmail())) {
            throw new EmailDuplicatedException(memberSignupRequest.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(memberSignupRequest.getPassword());
        Member member = Member.create(memberSignupRequest, encodedPassword);

        Member saved = memberRepository.save(member);
        return MemberResponse.create(saved);
    }


    @Transactional(readOnly = true)
    public TokenResponse login(MemberLoginRequest req) {
        Member member = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (Boolean.TRUE.equals(member.getDeleted())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        UUID memberId = member.getId();
        String role = member.getRole().name();

        String accessToken = tokenProvider.createAccessToken(memberId, role);


        return new TokenResponse("Bearer", accessToken, tokenProvider.getAccessTokenExpiresInSeconds());
    }

    public MemberResponse getMemberById(String name) {
        Member member = memberRepository.findById(UUID.fromString(name))
                .orElseThrow(() -> new IllegalArgumentException("member not found: " + name));

        return MemberResponse.create(member);
    }
}
