package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
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

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService,
                       WorkspaceRepository workspaceRepository, SlackClient slackClient) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.workspaceRepository = workspaceRepository;
        this.slackClient = slackClient;
    }

    public TokenResponse login(String code) {
        SlackUserInfo userInfo = slackClient.getUserInfoByCode(code);
        MemberCreateResponse memberCreateResponse = memberService.saveOrFind(userInfo);
        saveOrUpdateWorkspace(userInfo);

        return new TokenResponse(
            jwtTokenProvider.createToken(memberCreateResponse.getId().toString()),
            memberCreateResponse.getIsNew());
    }

    private void saveOrUpdateWorkspace(SlackUserInfo userInfo) {
        workspaceRepository.findByWorkspaceId(userInfo.getTeamId())
            .ifPresentOrElse(workspace -> workspace.updateName(userInfo.getTeamName()),
                () -> workspaceRepository.save(
                    new Workspace(null, userInfo.getTeamId(), userInfo.getTeamName(), null)));
    }

    public void installSlackApp(String code) {
        WorkspaceResponse botTokenResponse = slackClient.requestBotAccessToken(code);
        Optional<Workspace> workspace = workspaceRepository.findByWorkspaceId(
            botTokenResponse.getWorkspaceId());
        if (workspace.isEmpty()) {
            workspaceRepository.save(new Workspace(null, botTokenResponse.getWorkspaceId(),
                botTokenResponse.getWorkspaceName(), botTokenResponse.getAccessToken()));
            return;
        }
        Workspace existingWorkspace = workspace.get();
        existingWorkspace.updateName(botTokenResponse.getWorkspaceName());
        existingWorkspace.updateAccessToken(botTokenResponse.getAccessToken());
    }
}
