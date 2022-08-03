import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useAddSlackAppMutation } from '@/@hooks/@queries/service';
import { useOAuthLoginMutation } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

const LoginRedirect = () => {
  const navigate = useNavigate();
  const pathname = useLocation().pathname;

  const code = useGetSearchParam('code');

  const loginMutate = useOAuthLoginMutation();
  const addSlackAppMutate = useAddSlackAppMutation();

  useEffect(() => {
    if (code && pathname === PATH.LOGIN_REDIRECT) {
      loginMutate.mutate(code, {
        onSuccess() {
          navigate(PATH.LANDING);
        },
      });
    }
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [code, pathname, navigate]);

  useEffect(() => {
    if (code && pathname === PATH.DOWNLOAD_REDIRECT) {
      addSlackAppMutate.mutate(code, {
        onSuccess() {
          navigate(PATH.LANDING);
        },
      });
    }
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 addSlackAppMutate 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [code, pathname, navigate]);

  return <Loading>👻</Loading>;
};

export default LoginRedirect;
