import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import { useLoginMutation } from '@/@hooks/@queries/user';
import { client } from '@/apis';

const LoginRedirect = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const code = searchParams.get('code');

  const loginMutate = useLoginMutation();

  useEffect(() => {
    if (code) {
      loginMutate.mutate(code, {
        onSuccess(response) {
          const { accessToken } = response.data;

          localStorage.setItem('user-token', accessToken);

          client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

          navigate('/');
        },
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
  }, []);

  return <Loading>👻</Loading>;
};

export default LoginRedirect;
