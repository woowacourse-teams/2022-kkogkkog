import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import { useFetchUnregisteredCouponListByStatus } from '@/@hooks/@queries/unregistered-coupon';
import { Styled } from '@/@pages/coupon-list';
import { UnregisteredCoupon } from '@/types/unregistered-coupon/client';

import UnregisteredCouponItem from '../UnregisteredCouponItem';

interface UnregisteredCouponListSectionProps {
  onClickCouponItem: (coupon: UnregisteredCoupon) => void;
}

const UnregisteredCouponListSection = (props: UnregisteredCouponListSectionProps) => {
  const { onClickCouponItem } = props;

  const { couponListByStatus: issuedCouponList } = useFetchUnregisteredCouponListByStatus({
    type: 'ISSUED',
  });

  return (
    // @TODO: VerticalListContainer 스타일 공유하는 것 전체적으로 수정
    <Styled.VerticalListContainer>
      <VerticalCouponList
        couponList={issuedCouponList}
        CouponItem={UnregisteredCouponItem}
        onClickCouponItem={onClickCouponItem}
      />
    </Styled.VerticalListContainer>
  );
};

export default UnregisteredCouponListSection;
