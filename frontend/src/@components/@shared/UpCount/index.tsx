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

  const rAFId = useRef<number | null>(null);

  useEffect(() => {
    const animationHandler = (progress = 0) => {
      setCount(limit * easyOutExpo(progress));

      if (progress <= 1) {
        requestAnimationFrame(timestamp => animationHandler(timestamp / duration));
      }
    };

    rAFId.current = requestAnimationFrame(() => animationHandler(0));
  }, []);

  useEffect(() => {
    return () => {
      if (rAFId.current) {
        cancelAnimationFrame(rAFId.current);
      }
    };
  }, []);

  useEffect(() => {
    if (Math.ceil(count) === limit && rAFId.current) {
      cancelAnimationFrame(rAFId.current);
    }
  }, [count, limit]);

  return (
    <div className={className}>
      {count < limit ? Math.ceil(count) : limit}
      {children}
    </div>
  );
};

export default UpCount;
