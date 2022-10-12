import PageTemplate from '@/@components/@shared/PageTemplate';
import UnregisteredCouponExpiredTime from '@/@components/unregistered-coupon/UnregisteredCouponExpiredTime';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import { useFetchUnregisteredCoupon } from '@/@hooks/@queries/unregistered-coupon';
import { EXPIRATION_PERIOD } from '@/constants/unregisteredCoupon';
import { computeExpiredTime } from '@/utils/time';

import * as Styled from './style';

const UnregisteredCouponDetail = () => {
  const { unregisteredCoupon } = useFetchUnregisteredCoupon(1);

  if (!unregisteredCoupon) {
    return <div>hi</div>;
  }

  // const { couponType, couponMessage, couponCode, createdTime } = unregisteredCoupon;

  // const expiredTimeSeconds = computeExpiredTime(createdTime, EXPIRATION_PERIOD);

  return (
    <PageTemplate title='미등록 쿠폰 조회'>
      {/* <Styled.Root>
        <Styled.Top>
          <UnregisteredCouponExpiredTime createdTime={createdTime} />
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            <UnregisteredCouponItem {...unregisteredCoupon} />
          </Styled.CouponInner>
          <Styled.SubSection>
            <Styled.SubSectionTitle>미등록 쿠폰 코드</Styled.SubSectionTitle>
            <Styled.CodeContainer>{couponCode}</Styled.CodeContainer>
          </Styled.SubSection>
          <Styled.SubSection>
            <Styled.SubSectionTitle>쿠폰 메시지</Styled.SubSectionTitle>
            <Styled.DescriptionContainer>{couponMessage}</Styled.DescriptionContainer>
          </Styled.SubSection>
        </Styled.Main>
      </Styled.Root> */}
    </PageTemplate>
  );
};

export default UnregisteredCouponDetail;
