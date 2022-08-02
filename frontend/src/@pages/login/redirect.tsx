import { useEffect } from 'react';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useOAuthLoginMutation } from '@/@hooks/@queries/user';

const LoginRedirect = () => {
  const code = useGetSearchParam('code');

  const loginMutate = useOAuthLoginMutation();

  useEffect(() => {
    if (code) {
      loginMutate.mutate(code);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
  }, []);

  return <Loading>👻</Loading>;
};

export default LoginRedirect;
