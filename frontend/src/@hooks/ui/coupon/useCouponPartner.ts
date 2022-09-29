import { Coupon } from '@/types/coupon/client';
import { Member } from '@/types/user/client';

import { useFetchMe } from '../../@queries/user';

interface useCouponPartnerProps {
  sender: Member;
  receiver: Member;
}

function useCouponPartner(coupon: useCouponPartnerProps): {
  isSent: boolean;
  member: Member;
};
function useCouponPartner(coupon: Coupon | undefined): {
  isSent: boolean;
  member: Member | undefined;
};
function useCouponPartner(coupon: any) {
  const { me } = useFetchMe();

  const isSent = coupon?.sender.id === me?.id;

  return { isSent, member: isSent ? coupon?.receiver : coupon?.sender };
}

export default useCouponPartner;
