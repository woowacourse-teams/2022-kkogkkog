import { PropsWithChildren, useEffect } from 'react';

import { setPrevUrl } from '@/storage/session';

const PrevPathMemoization = ({ children }: PropsWithChildren) => {
  useEffect(() => {
    const prevPath = window.location.pathname;

    return () => {
      setPrevUrl(prevPath);
    };
  }, []);

  return <>{children}</>;
};

export default PrevPathMemoization;
