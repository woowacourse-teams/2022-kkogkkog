import { useState } from 'react';

import DownCount from '@/@components/@shared/DownCount';
import UpCount from '@/@components/@shared/UpCount';
import { EXPIRATION_PERIOD } from '@/constants/unregisteredCoupon';
import { YYYYMMDDhhmmss } from '@/types/utils';
import { computeDayHourMinSecByMS, computeExpiredTimeByPeriodMS } from '@/utils/time';

import * as Styled from './style';

interface UnregisteredCouponExpiredTimeProps {
  createdTime: YYYYMMDDhhmmss;
}
const UnregisteredCouponExpiredTime = (props: UnregisteredCouponExpiredTimeProps) => {
  const { createdTime } = props;

  const expiredTimeMS = computeExpiredTimeByPeriodMS(createdTime, EXPIRATION_PERIOD);

  const [remainingTime] = useState(expiredTimeMS - Date.now());

  const { day, hour, min } = computeDayHourMinSecByMS(remainingTime);

  return (
    <Styled.Root>
      <UpCount limit={day} duration={3000}>
        일
      </UpCount>
      <UpCount limit={hour} duration={3000}>
        시간
      </UpCount>
      <UpCount limit={min} duration={3000}>
        분
      </UpCount>
      <DownCount start={remainingTime} decreaseNumber={1000}>
        초
      </DownCount>
    </Styled.Root>
  );
};

export default UnregisteredCouponExpiredTime;
