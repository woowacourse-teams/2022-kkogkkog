import { useContext } from 'react';

import { ToastContext } from './../../@components/@shared/ToastProvider/index';

export const useToast = () => {
  const { displayMessage } = useContext(ToastContext);

  return {
    displayMessage,
  };
};
