package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
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
        Workspace workspace = saveOrUpdateWorkspace(
            new WorkspaceResponse(userInfo.getTeamId(), userInfo.getTeamName(), null));
        MemberCreateResponse memberCreateResponse = memberService.saveOrFind(userInfo, workspace);

        return new TokenResponse(
            jwtTokenProvider.createToken(memberCreateResponse.getId().toString()),
            memberCreateResponse.getIsNew());
    }

    public void installSlackApp(String code) {
        WorkspaceResponse workspaceResponse = slackClient.requestBotAccessToken(code);
        saveOrUpdateWorkspace(workspaceResponse);
    }

    private Workspace saveOrUpdateWorkspace(WorkspaceResponse workspaceResponse) {
        String workspaceId = workspaceResponse.getWorkspaceId();
        String workspaceName = workspaceResponse.getWorkspaceName();
        String accessToken = workspaceResponse.getAccessToken();
        return workspaceRepository.findByWorkspaceId(workspaceId)
            .map(workspace -> updateToMatchSlack(workspace, workspaceName, accessToken))
            .orElseGet(() -> workspaceRepository.save(
                new Workspace(null, workspaceId, workspaceName, accessToken)));
    }

    private Workspace updateToMatchSlack(Workspace workspace, String workspaceName,
                                         String accessToken) {
        workspace.updateName(workspaceName);
        workspace.updateAccessToken(accessToken);
        return workspace;
    }
}
