package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.auth.application.dto.MemberUpdateResponse;
import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.auth.support.JwtTokenProvider;
import com.woowacourse.kkogkkog.infrastructure.application.GoogleClient;
import com.woowacourse.kkogkkog.infrastructure.application.SlackClient;
import com.woowacourse.kkogkkog.infrastructure.dto.GoogleUserDto;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.WorkspaceResponse;
import com.woowacourse.kkogkkog.member.application.MemberService;
import com.woowacourse.kkogkkog.member.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberCreateRequest;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final WorkspaceRepository workspaceRepository;
    private final SlackClient slackClient;
    private final GoogleClient googleClient;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService,
                       WorkspaceRepository workspaceRepository, SlackClient slackClient,
                       GoogleClient googleClient) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.workspaceRepository = workspaceRepository;
        this.slackClient = slackClient;
        this.googleClient = googleClient;
    }

    public Long signUp(MemberCreateRequest memberCreateRequest) {
        String accessToken = memberCreateRequest.getAccessToken();
        SlackUserInfo userInfo = slackClient.requestUserInfo(accessToken);
        Workspace workspace = getWorkspace(userInfo);
        String nickname = memberCreateRequest.getNickname();
        return memberService.save(userInfo, workspace, nickname);
    }

    public Long signUpGoogle(MemberCreateRequest memberCreateRequest) {
        String accessToken = memberCreateRequest.getAccessToken();
        GoogleUserDto userDto = googleClient.requestUserInfo(accessToken);
        String nickname = memberCreateRequest.getNickname();
        return memberService.save(userDto, nickname);
    }

    public TokenResponse login(String code) {
        String accessToken = slackClient.requestAccessToken(code);
        SlackUserInfo userInfo = slackClient.requestUserInfo(accessToken);
        Workspace workspace = getWorkspace(userInfo);
        if (memberService.existsMember(userInfo)) {
            MemberUpdateResponse memberUpdateResponse = memberService.update(userInfo, workspace);
            return new TokenResponse(
                jwtTokenProvider.createToken(memberUpdateResponse.getId().toString()), false);
        }
        return new TokenResponse(accessToken, true);
    }

    public TokenResponse loginGoogle(String code) {
        String accessToken = googleClient.requestAccessToken(code);
        GoogleUserDto userDto = googleClient.requestUserInfo(accessToken);

        Optional<Member> member = memberService.findByEmail(userDto.getEmail());
        return member.map(it -> new TokenResponse(
                jwtTokenProvider.createToken(it.getId().toString()),
                false))
            .orElseGet(() -> new TokenResponse(accessToken, true));
    }

    public MemberCreateResponse loginByMemberId(Long id) {
        return new MemberCreateResponse(jwtTokenProvider.createToken(id.toString()));
    }

    private Workspace getWorkspace(SlackUserInfo userInfo) {
        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceId(userInfo.getTeamId());
        if (workspace.isPresent()) {
            Workspace existingWorkspace = workspace.get();
            existingWorkspace.updateName(userInfo.getTeamName());
            return existingWorkspace;
        }
        return workspaceRepository.save(
            new Workspace(null, userInfo.getTeamId(), userInfo.getTeamName(), null));
    }

    public void installSlackApp(String code) {
        WorkspaceResponse workspaceResponse = slackClient.requestBotAccessToken(code);
        String workspaceId = workspaceResponse.getWorkspaceId();
        String workspaceName = workspaceResponse.getWorkspaceName();
        String accessToken = workspaceResponse.getAccessToken();

        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceId(workspaceId);
        if (workspace.isPresent()) {
            Workspace existingWorkspace = workspace.get();
            existingWorkspace.updateName(workspaceName);
            existingWorkspace.updateAccessToken(accessToken);
            return;
        }
        workspaceRepository.save(new Workspace(null, workspaceId, workspaceName, accessToken));
    }
}
