import { useEffect } from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useSlackOAuthLogin } from '@/@hooks/business/user';
import { PATH } from '@/Router';

const SlackDirect = () => {
  const navigate = useNavigate();

  const pathname = useLocation().pathname;

  const code = useGetSearchParam('code');

  const { loginBySlackOAuth } = useSlackOAuthLogin();

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
          navigate(PATH.MAIN, { replace: true });
        }
      } catch (error) {
        navigate(PATH.MAIN, { replace: true });

        throw error;
      }
    };

    if (pathname === PATH.SLACK_LOGIN_REDIRECT) {
      slackLoginRedirect();
    }

    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return code ? <Loading /> : <Navigate to={PATH.MAIN} />;
};

export default SlackDirect;
