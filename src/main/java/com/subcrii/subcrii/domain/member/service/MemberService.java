package com.subcrii.subcrii.domain.member.service;

import com.subcrii.subcrii.domain.member.dto.MemberResponse;
import com.subcrii.subcrii.domain.member.dto.MemberSignupRequest;
import com.subcrii.subcrii.domain.member.entity.Member;
import com.subcrii.subcrii.domain.member.repository.MemberRepository;
import com.subcrii.subcrii.global.exception.EmailDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
}
