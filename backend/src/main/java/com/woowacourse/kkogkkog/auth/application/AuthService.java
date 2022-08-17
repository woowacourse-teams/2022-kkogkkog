package com.woowacourse.kkogkkog.auth.application;

import com.woowacourse.kkogkkog.application.MemberService;
import com.woowacourse.kkogkkog.auth.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.infrastructure.application.SlackClient;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.WorkspaceResponse;
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
        Workspace workspace = getWorkspace(userInfo);
        MemberCreateResponse memberCreateResponse = memberService.saveOrUpdate(userInfo, workspace);

        return new TokenResponse(
            jwtTokenProvider.createToken(memberCreateResponse.getId().toString()),
            memberCreateResponse.getIsNew());
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