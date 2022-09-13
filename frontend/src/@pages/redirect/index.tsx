import { useEffect } from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useAddSlackApp, useSlackOAuthLogin } from '@/@hooks/business/user';
import { PATH } from '@/Router';

const Redirect = () => {
  const navigate = useNavigate();

  const pathname = useLocation().pathname;

  const code = useGetSearchParam('code');

  const { loginBySlackOAuth } = useSlackOAuthLogin();
  const { addSlackApp } = useAddSlackApp();

  useEffect(() => {
    if (!code) {
      return;
    }

    const slackLoginRedirect = async () => {
      try {
        const response = await loginBySlackOAuth(code);

        if (response.isNew) {
          navigate(PATH.SIGNUP);
        } else {
          navigate(PATH.LANDING, { replace: true });
        }
      } catch (error) {
        navigate(PATH.LANDING, { replace: true });

        console.error(error);
      }
    };

    const addSlackAppRedirect = async () => {
      try {
        await addSlackApp(code);

        navigate(PATH.LANDING, { replace: true });
      } catch (error) {
        console.error(error);
      }
    };

    if (pathname === PATH.LOGIN_REDIRECT) {
      slackLoginRedirect();
    }

    if (pathname === PATH.DOWNLOAD_REDIRECT) {
      addSlackAppRedirect();
    }

    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return code ? <Loading /> : <Navigate to={PATH.LANDING} />;
};

export default Redirect;
