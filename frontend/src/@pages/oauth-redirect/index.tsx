import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useOAuthLogin } from '@/@hooks/business/user';
import { PATH } from '@/Router';
import { OAuthType } from '@/types/user/client';

interface OAuthRedirectProps {
  oAuthType: OAuthType;
}

const OAuthRedirect = (props: OAuthRedirectProps) => {
  const { oAuthType } = props;
  const code = useGetSearchParam('code');

  const { loginRedirect } = useOAuthLogin(oAuthType);

  useEffect(() => {
    if (!code) {
      return;
    }

    loginRedirect(code);
  });

  return code ? <Loading /> : <Navigate to={PATH.MAIN} />;
};

export default OAuthRedirect;
