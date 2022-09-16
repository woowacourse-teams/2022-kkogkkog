import { css } from '@emotion/react';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import { PATH } from '@/Router';

import * as Styled from './style';

const LandingPage = () => {
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
          <Link to={PATH.MAIN}>
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

export default LandingPage;
