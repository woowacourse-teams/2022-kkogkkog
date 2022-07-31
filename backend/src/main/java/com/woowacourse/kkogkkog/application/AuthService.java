package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.infrastructure.MemberCreateResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackRequester;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final SlackRequester slackRequester;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService,
        SlackRequester slackRequester) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.slackRequester = slackRequester;
    }

    @Transactional(readOnly = true)
    public TokenResponse login(String code) {
        SlackUserInfo userInfo = slackRequester.getUserInfoByCode(code);
        MemberCreateResponse memberCreateResponse = memberService.saveOrFind(userInfo);

        return new TokenResponse(
            jwtTokenProvider.createToken(memberCreateResponse.getId().toString()),
            memberCreateResponse.getIsNew());
    }
}
