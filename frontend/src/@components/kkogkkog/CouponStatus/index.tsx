import React from 'react';

import theme from '@/styles/theme';
import { COUPON_STATUS } from '@/types/client/kkogkkog';

import * as Styled from './style';

interface CouponStatusProps {
  status: 'REQUESTED' | 'READY' | 'ACCEPTED' | 'FINISHED';
}

const couponStatusMapper: Record<COUPON_STATUS, { backgroundColor: string; text: string }> = {
  REQUESTED: {
    backgroundColor: theme.colors.primary_500,
    text: '요청중',
  },
  READY: {
    backgroundColor: theme.colors.primary_300,
    text: '대기중',
  },
  ACCEPTED: {
    backgroundColor: theme.colors.green_500,
    text: '승인됨',
  },
  FINISHED: {
    backgroundColor: theme.colors.light_grey_100,
    text: '사용완료',
  },
};

const CouponStatus = (props: CouponStatusProps) => {
  const { status } = props;

  const { text, backgroundColor } = couponStatusMapper[status];

  return <Styled.Root backgroundColor={backgroundColor}>{text}</Styled.Root>;
};

export default CouponStatus;
