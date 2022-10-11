import BigCouponItem from '@/@components/coupon/CouponItem/big';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import { useFetchCouponListByStatus } from '@/@hooks/@queries/coupon';
import { Styled } from '@/@pages/coupon-list';
import { Coupon, COUPON_LIST_TYPE } from '@/types/coupon/client';

interface AcceptedCouponListSectionProps {
  couponListType: COUPON_LIST_TYPE;
  onClickCouponItem: (coupon: Coupon) => void;
}

const AcceptedCouponListSection = (props: AcceptedCouponListSectionProps) => {
  const { couponListType, onClickCouponItem } = props;

  const { couponListByStatus: acceptedCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'ACCEPTED' },
  });

  return (
    <Styled.VerticalListContainer>
      <VerticalCouponList
        couponList={acceptedCouponList}
        CouponItem={BigCouponItem}
        onClickCouponItem={onClickCouponItem}
      />
    </Styled.VerticalListContainer>
  );
};

export default AcceptedCouponListSection;
