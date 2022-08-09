package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.exception.workspace.WorkspaceNotFoundException;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final SlackClient slackClient;

    public WorkspaceService(WorkspaceRepository workspaceRepository, SlackClient slackClient) {
        this.workspaceRepository = workspaceRepository;
        this.slackClient = slackClient;
    }

    public void saveOrUpdate(SlackUserInfo slackUserInfo) {
        String workspaceId = slackUserInfo.getTeamId();
        String name = slackUserInfo.getTeamName();
        String imageUrl = slackUserInfo.getTeamImageUrl();

        workspaceRepository.findByWorkspaceId(workspaceId)
            .ifPresentOrElse(workspace -> updateToMatchSlack(workspace, name, imageUrl),
                () -> workspaceRepository.save(
                    new Workspace(null, workspaceId, name, imageUrl, null)));
    }

    private void updateToMatchSlack(Workspace workspace, String name, String imageUrl) {
        workspace.updateName(name);
        workspace.updateImageUrl(imageUrl);
    }

    public void installSlackApp(String code) {
        WorkspaceResponse botTokenResponse = slackClient.requestBotAccessToken(code);
        Workspace workspace = workspaceRepository.findByWorkspaceId(
                botTokenResponse.getWorkspaceId())
            .orElseThrow(WorkspaceNotFoundException::new);

        workspace.updateAccessToken(botTokenResponse.getAccessToken());
    }
}
