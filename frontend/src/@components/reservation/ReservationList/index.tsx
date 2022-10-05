import ReservationItem from '@/@components/reservation/ReservationItem';
import { Coupon } from '@/types/coupon/client';

import * as Styled from './style';

interface ReservationListProps {
  reservatedCouponList: Coupon[];
}

const ReservationList = (props: ReservationListProps) => {
  const { reservatedCouponList } = props;

  return (
    <Styled.Root>
      {reservatedCouponList.map(coupon => (
        <ReservationItem key={coupon.id} reservatedCoupon={coupon} />
      ))}
    </Styled.Root>
  );
};

export default ReservationList;
