import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Redirect from '@/@components/@shared/Redirect';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useOAuthLogin } from '@/@hooks/business/user';
import { PATH } from '@/Router';
import { OAuthType } from '@/types/user/client';

interface OAuthRedirectProps {
  oAuthType: OAuthType;
}

const OAuthRedirect = (props: OAuthRedirectProps) => {
  const { oAuthType } = props;

  const navigate = useNavigate();

  const code = useGetSearchParam('code');

  const { loginByOAuth } = useOAuthLogin(oAuthType);

  useEffect(() => {
    if (!code) {
      return;
    }

    const loginRedirect = async (code: string) => {
      try {
        const response = await loginByOAuth(code);

        if (response.isNew) {
          navigate(PATH.SIGNUP, { state: oAuthType });
        } else {
          navigate(PATH.MAIN, { replace: true });
        }
      } catch (error) {
        navigate(PATH.MAIN, { replace: true });

        throw error;
      }
    };

    loginRedirect(code);
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <Redirect code={code} />;
};

export default OAuthRedirect;
