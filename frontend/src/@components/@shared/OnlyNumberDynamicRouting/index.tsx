import { PropsWithChildren } from 'react';
import { Navigate, useParams } from 'react-router-dom';

import { PATH } from '@/Router';

const OnlyNumberDynamicRouting = (props: PropsWithChildren) => {
  const { children } = props;

  const params = useParams();

  if (!params) {
    return <Navigate to={PATH.ERROR} replace />;
  }

  if (Object.values(params).some(paramValue => isNaN(Number(paramValue)))) {
    return <Navigate to={PATH.ERROR} replace />;
  }

  return <>{children}</>;
};

export default OnlyNumberDynamicRouting;
