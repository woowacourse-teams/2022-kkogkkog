import { MouseEventHandler } from 'react';

import { useFetchMe } from '@/@hooks/@queries/user';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './style';

interface ReservationItemProps {
  coupon: CouponResponse;
  onClick?: MouseEventHandler;
}

const ReservationItem = (props: ReservationItemProps) => {
  const { me } = useFetchMe();

  return <Styled.Root>ReservationItem</Styled.Root>;
};

export default ReservationItem;
