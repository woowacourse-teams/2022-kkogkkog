import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useAddSlackApp, useSlackOAuthLogin } from '@/@hooks/user/useSlackOAuth';
import { PATH } from '@/Router';

const Redirect = () => {
  const pathname = useLocation().pathname;

  const code = useGetSearchParam('code');

  const { loginBySlackOAuth } = useSlackOAuthLogin();
  const { addSlackApp } = useAddSlackApp();

  useEffect(() => {
    if (!code) {
      return;
    }

    if (pathname === PATH.LOGIN_REDIRECT) {
      loginBySlackOAuth(code);
    }

    if (pathname === PATH.DOWNLOAD_REDIRECT) {
      addSlackApp(code);
    }

    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <Loading />;
};

export default Redirect;
