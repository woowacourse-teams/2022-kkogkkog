import { css } from '@emotion/react';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { client } from '@/apis';

const LoginRedirect = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const code = searchParams.get('code');

  const loginMutate = useMutation(() => client.get(`/login/token?code=${code}`), {
    onSuccess(data) {
      navigate('/');
      console.log('슬랙 로그인 성공했습니다.', data);
    },
  });

  useEffect(() => {
    loginMutate.mutate();
  }, [loginMutate]);

  return (
    <div
      css={css`
        background-color: red;
      `}
    >
      슬랙 로그인 redirect 페이지입니다.
    </div>
  );
};

export default LoginRedirect;
