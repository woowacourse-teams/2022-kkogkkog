import theme from '@/styles/theme';
import { COUPON_STATUS } from '@/types/coupon/client';
import { generateDateText } from '@/utils/time';

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
  meetingDate?: string;
  isSent: boolean;
}

const CouponStatus = (props: CouponStatusProps) => {
  const { status, isSent, meetingDate } = props;

  const meetingDateText = generateDateText(meetingDate);

  const { backgroundColor } = couponStatusMapper(isSent)[status];

  if (status === 'READY') {
    return <Styled.HiddenRoot backgroundColor={backgroundColor}>대기중</Styled.HiddenRoot>;
  }

  return <Styled.Root backgroundColor={backgroundColor}>{meetingDateText}</Styled.Root>;
};

export default CouponStatus;
