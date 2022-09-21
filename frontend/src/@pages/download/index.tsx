import * as Styled from './style';

const DownloadPage = () => {
  return (
    <Styled.Root>
      <Styled.Title>쿠폰 알림은 어떻게 받을 수 있나요?</Styled.Title>
      <Styled.Description>
        슬랙 워크 스페이스에 꼭꼭 앱을 추가하면
        <br />
        쿠폰 알림을 받을 수 있어요.
      </Styled.Description>
      <Styled.SlackAddButtonInner>
        {PRODUCT_ENV === 'production' ? (
          <a href='https://slack.com/oauth/v2/authorize?client_id=3711114175136.3863202543751&scope=chat:write,chat:write.public,users:read.email,users:read&user_scope=email,openid,profile&redirect_uri=https://kkogkkog.com/download/redirect'>
            <img
              alt='Add to Slack'
              width={139}
              height={40}
              src='https://platform.slack-edge.com/img/add_to_slack.png'
              srcSet='https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x'
            />
          </a>
        ) : (
          <a href='https://slack.com/oauth/v2/authorize?client_id=3711114175136.3863202543751&scope=chat:write,chat:write.public,users:read.email,users:read&user_scope=email,openid,profile&redirect_uri=https://dev.kkogkkog.com/download/redirect'>
            <img
              alt='Add to Slack'
              width={139}
              height={40}
              src='https://platform.slack-edge.com/img/add_to_slack.png'
              srcSet='https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x'
            />
          </a>
        )}
      </Styled.SlackAddButtonInner>
    </Styled.Root>
  );
};

export default DownloadPage;
