import { PropsWithChildren, ReactElement } from 'react';

interface CustomSuspenseProps {
  fallback: ReactElement;
  isLoading: boolean;
}
const CustomSuspense = (props: PropsWithChildren<CustomSuspenseProps>) => {
  const { fallback, isLoading, children } = props;

  if (isLoading) {
    return fallback;
  }

  return <>{children}</>;
};

export default CustomSuspense;
