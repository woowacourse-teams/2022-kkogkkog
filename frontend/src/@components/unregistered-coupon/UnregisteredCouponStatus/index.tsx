import theme from '@/styles/theme';
import { UNREGISTERED_COUPON_STATUS } from '@/types/unregistered-coupon/client';

import * as Styled from './style';

const UnregisteredCouponStatusMapper: Record<
  UNREGISTERED_COUPON_STATUS,
  { backgroundColor: string; text: string }
> = {
  ISSUED: {
    backgroundColor: theme.colors.primary_500,
    text: '미등록',
  },
  REGISTERED: {
    backgroundColor: theme.colors.green_500,
    text: '등록',
  },
  EXPIRED: {
    backgroundColor: theme.colors.light_grey_100,
    text: '만료',
  },
};

interface UnregisteredCouponStatusProps {
  status: UNREGISTERED_COUPON_STATUS;
}

const UnregisteredCouponStatus = (props: UnregisteredCouponStatusProps) => {
  const { status } = props;

  const { backgroundColor, text } = UnregisteredCouponStatusMapper[status];

  return <Styled.Root backgroundColor={backgroundColor}>{text}</Styled.Root>;
};

export default UnregisteredCouponStatus;
