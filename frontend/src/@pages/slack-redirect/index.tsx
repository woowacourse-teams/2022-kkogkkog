import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useOAuthLogin } from '@/@hooks/business/user';
import { PATH } from '@/Router';

const SlackDirect = () => {
  const code = useGetSearchParam('code');

  const { slackLoginRedirect } = useOAuthLogin('slack');

  useEffect(() => {
    if (!code) {
      return;
    }

    slackLoginRedirect(code);
  });

  return code ? <Loading /> : <Navigate to={PATH.MAIN} />;
};

export default SlackDirect;
