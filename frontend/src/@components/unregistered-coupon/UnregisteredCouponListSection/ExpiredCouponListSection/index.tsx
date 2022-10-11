import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import { useFetchUnregisteredCouponListByStatus } from '@/@hooks/@queries/unregistered-coupon';
import { Styled } from '@/@pages/coupon-list';
import { UnregisteredCoupon } from '@/types/unregistered-coupon/client';

import UnregisteredCouponItem from '../../UnregisteredCouponItem';

interface ExpiredCouponListSectionProps {
  onClickCouponItem: (coupon: UnregisteredCoupon) => void;
}

const ExpiredCouponListSection = (props: ExpiredCouponListSectionProps) => {
  const { onClickCouponItem } = props;

  const { unregisteredCouponListByStatus: expiredCouponList } =
    useFetchUnregisteredCouponListByStatus({
      type: 'EXPIRED',
    });

  return (
    // @TODO: VerticalListContainer 스타일 공유하는 것 전체적으로 수정
    <Styled.VerticalListContainer>
      <VerticalCouponList
        couponList={expiredCouponList}
        CouponItem={UnregisteredCouponItem}
        onClickCouponItem={onClickCouponItem}
      />
    </Styled.VerticalListContainer>
  );
};

export default ExpiredCouponListSection;
