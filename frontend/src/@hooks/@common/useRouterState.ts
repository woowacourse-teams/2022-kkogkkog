import { useLocation } from 'react-router-dom';

import { hasKey } from '@/types/utils';

const useRouterState = <ValueType>(key: string) => {
  const { state } = useLocation();

  if (hasKey<ValueType>(state, key)) {
    return state[key];
  }

  return null;
};

export default useRouterState;
