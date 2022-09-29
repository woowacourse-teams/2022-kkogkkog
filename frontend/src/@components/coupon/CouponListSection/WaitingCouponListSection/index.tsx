import { useFetchCouponListByStatus } from '@/@hooks/@queries/coupon';
import { Styled } from '@/@pages/coupon-list';
import { Coupon, COUPON_LIST_TYPE } from '@/types/coupon/client';

import BigCouponItem from '../../CouponItem/big';
import VerticalCouponList from '../../CouponList/vertical';

interface WaitingCouponListSectionProps {
  couponListType: COUPON_LIST_TYPE;
  onClickCouponItem: (coupon: Coupon) => void;
}

const WaitingCouponListSection = (props: WaitingCouponListSectionProps) => {
  const { couponListType, onClickCouponItem } = props;

  const { couponListByStatus: readyCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'READY' },
  });
  const { couponListByStatus: requestedCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'REQUESTED' },
  });

  return (
    <Styled.VerticalListContainer>
      <VerticalCouponList
        couponList={[...readyCouponList, ...requestedCouponList]}
        CouponItem={BigCouponItem}
        onClickCouponItem={onClickCouponItem}
      />
    </Styled.VerticalListContainer>
  );
};

export default WaitingCouponListSection;
