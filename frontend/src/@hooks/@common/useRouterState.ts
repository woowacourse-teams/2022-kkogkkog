import { useLocation } from 'react-router-dom';

export const useRouterState = (key: string) => {
  const { state } = useLocation();

  if (state === null) {
    return null;
  }

  if (key in state) {
    return state[key];
  }

  return null;
};
