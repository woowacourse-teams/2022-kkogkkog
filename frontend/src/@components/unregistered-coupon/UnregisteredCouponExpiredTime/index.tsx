import { useEffect, useRef, useState } from 'react';

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

  const [remainingTime, setRemainingTime] = useState(
    computeExpiredTimeByPeriodMS(createdTime, EXPIRATION_PERIOD) - Date.now()
  );

  const rAFId = useRef<number | null>(null);

  useEffect(() => {
    const remainingTimeSetter = (prevSecond: number, timestamp: number) => {
      const currentSecond = Math.floor(timestamp / 1000);

      if (prevSecond < currentSecond) {
        setRemainingTime(prev => prev - 1000);
      }

      if (remainingTime / 1000 - currentSecond > 0) {
        // 줄어들 시간이 없는 경우
        requestAnimationFrame(timestamp => remainingTimeSetter(currentSecond, timestamp));
      }
    };

    rAFId.current = requestAnimationFrame(timestamp => remainingTimeSetter(0, timestamp));
  }, []);

  useEffect(() => {
    return () => {
      if (rAFId.current) {
        cancelAnimationFrame(rAFId.current);
      }
    };
  }, []);

  useEffect(() => {
    if (Math.floor(remainingTime) < 0 && rAFId.current) {
      cancelAnimationFrame(rAFId.current);
    }
  }, [remainingTime]);

  const { day, hour, min, sec } = computeDayHourMinSecByMS(remainingTime);

  return (
    <Styled.Root>
      {remainingTime > 0 ? (
        <>
          <UpCount limit={day} duration={3000}>
            일
          </UpCount>
          <UpCount limit={hour} duration={3000}>
            시간
          </UpCount>
          <UpCount limit={min} duration={3000}>
            분
          </UpCount>
          <div>{sec}초</div>
        </>
      ) : (
        <div>받을 수 있는 기간이 지났어요 !</div>
      )}
    </Styled.Root>
  );
};

export default UnregisteredCouponExpiredTime;
