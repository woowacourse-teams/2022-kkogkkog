import ReservationItem from '@/@components/reservation/ReservationItem';
import { CouponResponse } from '@/types/remote/response';

interface ReservationListProps {
  couponList: CouponResponse[];
  onClickReservationItem?: (coupon: CouponResponse) => void;
}

const ReservationList = (props: ReservationListProps) => {
  const { couponList, onClickReservationItem } = props;

  return (
    <div>
      {couponList.map(coupon => (
        <ReservationItem
          key={coupon.id}
          coupon={coupon}
          onClick={() => onClickReservationItem?.(coupon)}
        />
      ))}
    </div>
  );
};

export default ReservationList;
