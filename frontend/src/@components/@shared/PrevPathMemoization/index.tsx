import { PropsWithChildren, useEffect } from 'react';

export const { getPrevURL, setPrevURL } = (() => {
  const sessionStorageKey = 'prevURL';

  return {
    getPrevURL() {
      return sessionStorage.getItem(sessionStorageKey) ?? '';
    },
    setPrevURL(prevPath: string) {
      sessionStorage.setItem(sessionStorageKey, prevPath);
    },
  };
})();

const PrevPathMemoization = ({ children }: PropsWithChildren) => {
  useEffect(() => {
    const prevPath = window.location.pathname;

    return () => {
      setPrevURL(prevPath);
    };
  }, []);

  return <>{children}</>;
};

export default PrevPathMemoization;
