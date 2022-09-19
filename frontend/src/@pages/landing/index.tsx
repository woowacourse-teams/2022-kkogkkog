import { css } from '@emotion/react';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import { useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

import * as Styled from './style';

const LandingPage = () => {
  const { me } = useFetchMe();

  return (
    <PageTemplate title='꼭꼭' hasHeader={false}>
      <Styled.Root>
        <Styled.Branding>
          <img src='/assets/images/landing_logo.png' alt='로고' width='86' />

          <div>
            <Styled.ExtraBold>꼭꼭</Styled.ExtraBold>으로
            <br />
            당신의 마음을 전달해보세요!
          </div>

          <Styled.AdditionalExplanation>
            시간을 보내고 싶어하는 사람들이 있을지 모릅니다.
          </Styled.AdditionalExplanation>
        </Styled.Branding>
        <Position
          position='absolute'
          bottom='50px'
          css={css`
            width: 100%;
            padding: 30px;
          `}
        >
          <Link to={me ? PATH.MAIN : PATH.LOGIN}>
            <Button css={Styled.ExtendedButton}>
              꼭꼭 시작하기 <Icon iconName='airplane' />
            </Button>
          </Link>
        </Position>
      </Styled.Root>
    </PageTemplate>
  );
};

export default LandingPage;
