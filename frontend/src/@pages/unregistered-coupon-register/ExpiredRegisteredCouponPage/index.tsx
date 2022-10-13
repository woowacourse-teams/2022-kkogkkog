import { useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import landingLogoImage from '@/assets/images/landing_logo.png';
import { PATH } from '@/Router';

import * as Styled from './style';

const ExpiredUnregisteredCouponPage = () => {
  const navigate = useNavigate();

  return (
    <PageTemplate title='꼭꼭' hasHeader={false}>
      <Styled.Root>
        <img src={landingLogoImage} alt='로고' width='86' />
        <Styled.Description>해당 쿠폰은 만료 기간(7일)이 지났습니다.</Styled.Description>
        <Styled.ButtonInner>
          <Button onClick={() => navigate(PATH.MAIN)}>홈으로 가기</Button>
        </Styled.ButtonInner>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ExpiredUnregisteredCouponPage;
