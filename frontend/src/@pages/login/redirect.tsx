import { css } from '@emotion/react';
import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { useLoginMutation } from '@/@hooks/@queries/user';

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

          // slack API로 Auth Logic을 모두 갈아낀운 후 주석 삭제
          // client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

          navigate('/');
        },
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
  }, []);

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
