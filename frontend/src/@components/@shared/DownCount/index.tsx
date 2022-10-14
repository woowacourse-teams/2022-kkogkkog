import { PropsWithChildren, useEffect, useRef, useState } from 'react';

import { computeDayHourMinSecByMS } from '@/utils/time';

interface DownCountProps {
  className?: string;
  start: number;
  decreaseNumber: number;
}

const DownCount = (props: PropsWithChildren<DownCountProps>) => {
  const { start, decreaseNumber, className, children } = props;

  const [count, setCount] = useState(start);

  const rAFId = useRef<number | null>(null);

  useEffect(() => {
    const remainingTimeSetter = (prevSecond: number, timestamp: number) => {
      const currentSecond = Math.floor(timestamp / decreaseNumber);

      if (prevSecond < currentSecond) {
        setCount(prev => prev - 1000);
      }

      if (count > 0) {
        requestAnimationFrame(timestamp => remainingTimeSetter(currentSecond, timestamp));
      }
    };

    rAFId.current = requestAnimationFrame(timestamp => remainingTimeSetter(0, timestamp));
  }, []);

  useEffect(() => {
    if (Math.floor(count) === 0 && rAFId.current) {
      cancelAnimationFrame(rAFId.current);
    }
  }, [count, start]);

  const { sec } = computeDayHourMinSecByMS(count);

  return (
    <div className={className}>
      {Math.floor(sec)}
      {children}
    </div>
  );
};

export default DownCount;
