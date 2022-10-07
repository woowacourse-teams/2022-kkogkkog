import React from 'react';
import { Link } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import { PATH } from '@/Router';

import * as Styled from './style';

const CouponCreateSelectPage = () => {
  return (
    <PageTemplate title='쿠폰 선택'>
      <Styled.Root>
        <Styled.Container>
          <Link to={PATH.COUPON_CREATE} css={Styled.LinkButton} replace>
            쿠폰 보내러가기
          </Link>
          <Link to={PATH.UNREGISTERED_COUPON_CREATE} css={Styled.LinkButton} replace>
            미등록 쿠폰 보내러가기
          </Link>
        </Styled.Container>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponCreateSelectPage;
