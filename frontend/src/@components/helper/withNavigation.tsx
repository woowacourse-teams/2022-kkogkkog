import { ComponentType } from 'react';
import type { NavigateFunction } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

export interface NavigationProps {
  navigate: NavigateFunction;
}

function withNavigation<T>(Component: ComponentType<T & NavigationProps>) {
  const WithNavigation = (props: T) => {
    const navigate = useNavigate();

    return <Component navigate={navigate} {...props} />;
  };

  return WithNavigation;
}

export default withNavigation;
