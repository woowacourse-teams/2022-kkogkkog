import { Coupon } from '@/types/coupon/client';

import { useFetchMe } from '../../@queries/user';

const useCouponPartner = (coupon: Coupon | undefined) => {
  const { me } = useFetchMe();

  const isSent = coupon?.sender.id === me?.id;

  return { isSent, member: isSent ? coupon?.receiver : coupon?.sender };
};

export default useCouponPartner;
