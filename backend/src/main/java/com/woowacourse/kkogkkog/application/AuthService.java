package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberWrongPasswordException;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Member findMember = memberRepository.findByEmail(tokenRequest.getEmail())
                .orElseThrow(MemberNotFoundException::new);
        if (findMember.isNotSamePassword(tokenRequest.getPassword())) {
            throw new MemberWrongPasswordException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(findMember.getId().toString()));
    }
}
