import { useParams } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import UnregisteredCouponExpiredTime from '@/@components/unregistered-coupon/UnregisteredCouponExpiredTime';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import { useFetchUnregisteredCouponById } from '@/@hooks/@queries/unregistered-coupon';
import NotFoundPage from '@/@pages/404';

import * as Styled from './style';

const UnregisteredCouponDetail = () => {
  const { unregisteredCouponId } = useParams();

  const { unregisteredCoupon } = useFetchUnregisteredCouponById(Number(unregisteredCouponId));

  if (!unregisteredCoupon) {
    return <NotFoundPage />;
  }

  const { couponMessage, createdTime } = unregisteredCoupon;

  return (
    <PageTemplate.ExtendedStyleHeader title='미등록 쿠폰'>
      <Styled.Root>
        <Styled.Top>
          <UnregisteredCouponExpiredTime createdTime={createdTime} />
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            <UnregisteredCouponItem {...unregisteredCoupon} />
          </Styled.CouponInner>
          <Styled.SubSection>
            <Styled.SubSectionTitle>쿠폰 메시지</Styled.SubSectionTitle>
            <Styled.DescriptionContainer>{couponMessage}</Styled.DescriptionContainer>
          </Styled.SubSection>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default UnregisteredCouponDetail;
