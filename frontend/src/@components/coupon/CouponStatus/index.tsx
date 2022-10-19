import theme from '@/styles/theme';
import { COUPON_MEETING_DATE, COUPON_STATUS } from '@/types/coupon/client';
import { generateDateKR } from '@/utils/time';

import * as Styled from './style';

const couponStatusMapper = (
  isSent: boolean
): Record<COUPON_STATUS, { backgroundColor: string }> => ({
  REQUESTED: {
    backgroundColor: theme.colors.primary_500,
  },
  READY: {
    backgroundColor: theme.colors.primary_300,
  },
  ACCEPTED: {
    backgroundColor: theme.colors.green_500,
  },
  FINISHED: {
    backgroundColor: theme.colors.light_grey_100,
  },
});

interface CouponStatusProps {
  status: 'REQUESTED' | 'READY' | 'ACCEPTED' | 'FINISHED';
  meetingDate: COUPON_MEETING_DATE | null;
  isSent: boolean;
}

const CouponStatus = (props: CouponStatusProps) => {
  const { status, isSent, meetingDate } = props;

  const { backgroundColor } = couponStatusMapper(isSent)[status];

  if (!meetingDate) {
    /** meetingDate가 없다면 대기중 */
    return <Styled.HiddenRoot />;
  }

  return <Styled.Root backgroundColor={backgroundColor}>{generateDateKR(meetingDate)}</Styled.Root>;
};

export default CouponStatus;
