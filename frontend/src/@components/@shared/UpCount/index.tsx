import { PropsWithChildren, useEffect, useRef, useState } from 'react';

import { easyOutExpo } from '@/utils/animation';

interface UpCountProps {
  className?: string;
  limit: number;
  duration: number;
}

const UpCount = (props: PropsWithChildren<UpCountProps>) => {
  const { className, limit, duration, children } = props;

  const [count, setCount] = useState(0);

  const reqId = useRef<number | null>(null);

  useEffect(() => {
    const animationHandler = (progress = 0) => {
      setCount(limit * easyOutExpo(progress));

      if (progress <= 1) {
        requestAnimationFrame(timestamp => animationHandler(timestamp / duration));
      }
    };

    reqId.current = requestAnimationFrame(() => animationHandler(0));
  }, [duration, limit]);

  useEffect(() => {
    if (Math.ceil(count) === limit && reqId.current) {
      cancelAnimationFrame(reqId.current);
    }
  }, [count, limit]);

  return (
    <div className={className}>
      {Math.ceil(count)}
      {children}
    </div>
  );
};

export default UpCount;
