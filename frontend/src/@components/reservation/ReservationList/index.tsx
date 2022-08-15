import ReservationItem from '@/@components/reservation/ReservationItem';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './style';

interface ReservationListProps {
  reservatedCouponList: CouponResponse[];
}

const ReservationList = (props: ReservationListProps) => {
  const { reservatedCouponList } = props;

  return (
    <Styled.Root>
      {reservatedCouponList.map(coupon => (
        <ReservationItem key={coupon.couponId} reservatedCoupon={coupon} />
      ))}
    </Styled.Root>
  );
};

export default ReservationList;
