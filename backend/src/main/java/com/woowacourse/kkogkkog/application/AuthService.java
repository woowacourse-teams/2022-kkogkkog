package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.MemberOAuthResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.application.dto.TokenOAuthResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberWrongInputException;
import com.woowacourse.kkogkkog.infrastructure.SlackRequester;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final SlackRequester slackRequester;

    public AuthService(MemberRepository memberRepository,
        JwtTokenProvider jwtTokenProvider,
        MemberService memberService, SlackRequester slackRequester) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.slackRequester = slackRequester;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        Member findMember = memberRepository.findByEmail(tokenRequest.getEmail())
            .orElseThrow(MemberNotFoundException::new);
        if (findMember.isNotSamePassword(tokenRequest.getPassword())) {
            throw new MemberWrongInputException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(findMember.getId().toString()));
    }

    @Transactional(readOnly = true)
    public TokenOAuthResponse loginByOAuth(String code) {
        SlackUserInfo userInfoResponse = slackRequester.getUserInfoByCode(code);
        MemberOAuthResponse memberOAuthResponse = memberService.saveOrFind(userInfoResponse);

        return new TokenOAuthResponse(
            jwtTokenProvider.createToken(memberOAuthResponse.getId().toString()),
            memberOAuthResponse.getIsCreated());
    }
}
