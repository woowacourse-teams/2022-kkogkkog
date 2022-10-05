import { useFetchMe } from '@/@hooks/@queries/user';
import { Coupon } from '@/types/coupon/client';
import { Member } from '@/types/user/client';

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
