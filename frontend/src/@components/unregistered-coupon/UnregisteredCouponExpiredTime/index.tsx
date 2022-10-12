import { useEffect, useState } from 'react';

import UpCount from '@/@components/@shared/UpCount';
import { EXPIRATION_PERIOD } from '@/constants/unregisteredCoupon';
import { YYYYMMDDhhmmss } from '@/types/utils';
import { computeDayHourMinSecByMS, computeExpiredTime } from '@/utils/time';

import * as Styled from './style';

interface UnregisteredCouponExpiredTimeProps {
  createdTime: YYYYMMDDhhmmss;
}
const UnregisteredCouponExpiredTime = (props: UnregisteredCouponExpiredTimeProps) => {
  const { createdTime } = props;

  const expiredTimeMS = computeExpiredTime(createdTime, EXPIRATION_PERIOD);

  const [remainingTime, setRemainingTime] = useState(expiredTimeMS - Date.now());

  useEffect(() => {
    const remainingTimeSetter = (prevSecond: number, timestamp: number) => {
      const currentSecond = Math.floor(timestamp / 1000);

      if (prevSecond < currentSecond) {
        setRemainingTime(prev => prev - 1000);
      }

      requestAnimationFrame(timestamp => remainingTimeSetter(currentSecond, timestamp));
    };

    requestAnimationFrame(timestamp => remainingTimeSetter(0, timestamp));
  }, []);

  const { day, hour, min, sec } = computeDayHourMinSecByMS(remainingTime);

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
      <Styled.SecContainer>{sec}초</Styled.SecContainer>
    </Styled.Root>
  );
};

export default UnregisteredCouponExpiredTime;
