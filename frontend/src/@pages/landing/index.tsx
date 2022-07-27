import { css } from '@emotion/react';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import CustomSuspense from '@/@components/@shared/CustomSuspense';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useKkogKkogList } from '@/@hooks/kkogkkog/useKkogKkogList';
import useMe from '@/@hooks/user/useMe';
import { PATH } from '@/Router';

import * as Styled from './style';

const UnAuthorizedLanding = () => {
  return (
    <PageTemplate title='꼭꼭' hasHeader={false}>
      <Styled.UnAuthorizedRoot>
        <Styled.UnAuthorizedContainer>
          <img src='/assets/images/landing_logo.png' alt='로고' width='86' />

          <div>
            <Styled.ExtraBold>꼭꼭</Styled.ExtraBold>으로
            <br />
            당신의 마음을 전달해보세요!
          </div>

          <Styled.AdditionalExplanation>
            시간을 보내고 싶어하는 사람들이 있을지 모릅니다.
          </Styled.AdditionalExplanation>
        </Styled.UnAuthorizedContainer>
        <Position
          position='absolute'
          bottom='50px'
          css={css`
            width: 100%;
            padding: 30px;
          `}
        >
          <Link to='/login'>
            <Button
              css={css`
                display: flex;
                padding: 15px;
                justify-content: center;
                align-items: center;

                font-size: 16px;

                gap: 15px;
              `}
            >
              꼭꼭 시작하기 <Icon iconName='airplane' />
            </Button>
          </Link>
        </Position>
      </Styled.UnAuthorizedRoot>
    </PageTemplate>
  );
};

/** ListHeaderContainer는 어디에 있어야하는가? */

const AuthorizedLanding = () => {
  const { kkogkkogList, isLoading } = useKkogKkogList();

  return (
    <PageTemplate.LandingPage title='꼭꼭'>
      <Styled.Root>
        <Styled.CreateCouponContainer>
          <div>
            <Styled.ExtraBold>꼭꼭</Styled.ExtraBold>으로
            <br />
            당신의 마음을 전달해보세요!
          </div>

          <Styled.AdditionalExplanation>
            시간을 보내고 싶어하는 사람들이 있을지 모릅니다.
          </Styled.AdditionalExplanation>
          <Link to={PATH.KKOGKKOG_CREATE}>
            <Position>
              <Button css={Styled.ExtendedButton}>
                꼭꼭 보내러가기
                <Position
                  position='absolute'
                  css={css`
                    right: 5px;
                    bottom: 50%;

                    transform: translateY(50%);
                  `}
                >
                  <Icon iconName='plus' size='32' />
                </Position>
              </Button>
            </Position>
          </Link>
        </Styled.CreateCouponContainer>

        <CustomSuspense fallback={<KkogKkogList.Skeleton />} isLoading={isLoading}>
          <Styled.ListContainer>
            <div>
              <Styled.ListTitle>
                <span>받은 쿠폰</span>
                <Link to={PATH.KKOGKKOG_LIST} css={Styled.ExtendedLink}>
                  더보기
                </Link>
              </Styled.ListTitle>
              <KkogKkogList.Slide kkogkkogList={kkogkkogList && kkogkkogList.received} />
            </div>

            <div>
              <Styled.ListTitle>
                <span>보낸 쿠폰</span>
                <Link to={PATH.KKOGKKOG_LIST} css={Styled.ExtendedLink}>
                  더보기
                </Link>
              </Styled.ListTitle>
              <KkogKkogList.Slide kkogkkogList={kkogkkogList && kkogkkogList.sent} />
            </div>
          </Styled.ListContainer>
        </CustomSuspense>
      </Styled.Root>
    </PageTemplate.LandingPage>
  );
};

const LandingPage = () => {
  const { me } = useMe();

  return me ? <AuthorizedLanding /> : <UnAuthorizedLanding />;
};

export default LandingPage;
