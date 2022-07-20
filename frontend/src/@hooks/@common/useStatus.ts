import { useState } from 'react';

export const useStatus = <T>(defaultStatus: T) => {
  const [status, setStatus] = useState<T>(defaultStatus);

  return {
    status,
    changeStatus(status: T) {
      setStatus(status);
    },
  };
};
