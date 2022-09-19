import { PropsWithChildren, useEffect } from 'react';

import { prevUrlSessionStorage } from '@/storage/session';

const PrevPathMemoization = ({ children }: PropsWithChildren) => {
  useEffect(() => {
    const prevPath = window.location.pathname;

    return () => {
      prevUrlSessionStorage.set(prevPath);
    };
  }, []);

  return <>{children}</>;
};

export default PrevPathMemoization;
