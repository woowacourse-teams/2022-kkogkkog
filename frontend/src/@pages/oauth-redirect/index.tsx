import { useEffect } from 'react';

import Redirect from '@/@components/@shared/Redirect';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useOAuthLogin } from '@/@hooks/business/user';
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

  return <Redirect code={code} />;
};

export default OAuthRedirect;
