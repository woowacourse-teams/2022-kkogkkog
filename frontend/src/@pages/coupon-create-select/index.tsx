import { Link } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import { PATH } from '@/Router';

import * as Styled from './style';

const CouponCreateSelectPage = () => {
  return (
    <PageTemplate title='쿠폰 선택'>
      <Styled.Root>
        <Styled.Container>
          <Link to={PATH.COUPON_CREATE} css={Styled.LinkButton}>
            꼭꼭 회원에게 쿠폰 보내기
          </Link>
          <Link to={PATH.UNREGISTERED_COUPON_CREATE} css={Styled.LinkButton}>
            링크로 쿠폰 보내기
          </Link>
        </Styled.Container>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponCreateSelectPage;
