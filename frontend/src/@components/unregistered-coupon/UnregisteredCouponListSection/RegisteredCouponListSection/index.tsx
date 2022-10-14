import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import { useFetchUnregisteredCouponListByStatus } from '@/@hooks/@queries/unregistered-coupon';
import { Styled } from '@/@pages/coupon-list';
import { UnregisteredCouponResponse } from '@/types/unregistered-coupon/remote';

import UnregisteredCouponItem from '../../UnregisteredCouponItem';

interface RegisteredCouponListSectionProps {
  onClickCouponItem: (coupon: UnregisteredCouponResponse) => void;
}

const RegisteredCouponListSection = (props: RegisteredCouponListSectionProps) => {
  const { onClickCouponItem } = props;

  const { unregisteredCouponListByStatus: registeredCoupon } =
    useFetchUnregisteredCouponListByStatus({
      type: 'REGISTERED',
    });

  return (
    // @TODO: VerticalListContainer 스타일 공유하는 것 전체적으로 수정
    <Styled.VerticalListContainer>
      <VerticalCouponList
        couponList={registeredCoupon}
        CouponItem={UnregisteredCouponItem}
        onClickCouponItem={onClickCouponItem}
      />
    </Styled.VerticalListContainer>
  );
};

export default RegisteredCouponListSection;
