import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useOAuthLogin } from '@/@hooks/business/user';
import { PATH } from '@/Router';

const GoogleDirect = () => {
  const code = useGetSearchParam('code');

  const { googleLoginRedirect } = useOAuthLogin('google');

  useEffect(() => {
    if (!code) {
      return;
    }

    googleLoginRedirect(code);
  });

  return code ? <Loading /> : <Navigate to={PATH.MAIN} />;
};

export default GoogleDirect;
