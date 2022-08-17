import { useContext } from 'react';

import { LoadingContext } from '@/@components/@shared/LoadingProvider';

export const useLoading = () => {
  const { showLoading, hideLoading } = useContext(LoadingContext);

  return {
    showLoading,
    hideLoading,
  };
};
