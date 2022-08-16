import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import Loading from '@/@components/@shared/Loading';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useToast } from '@/@hooks/@common/useToast';
import { useAddSlackAppMutation } from '@/@hooks/@queries/service';
import { useOAuthLoginMutation } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

const Redirect = () => {
  const { displayMessage } = useToast();

  const navigate = useNavigate();
  const pathname = useLocation().pathname;

  const code = useGetSearchParam('code');

  const loginMutate = useOAuthLoginMutation();
  const addSlackAppMutate = useAddSlackAppMutation();

  useEffect(() => {
    if (!code) return;

    if (pathname === PATH.LOGIN_REDIRECT) {
      loginMutate.mutate(code, {
        onSuccess(response) {
          const { isNew } = response.data;

          if (isNew) {
            navigate(PATH.PROFILE_EDIT);

            displayMessage('회원가입에 성공했어요. 닉네임을 변경해볼까요?', false);
          } else {
            navigate(PATH.LANDING);

            displayMessage('로그인에 성공하였습니다.', false);
          }
        },
        onError() {
          navigate(PATH.LANDING);
        },
      });
    }

    if (pathname === PATH.DOWNLOAD_REDIRECT) {
      addSlackAppMutate.mutate(code, {
        onSuccess() {
          navigate(PATH.LANDING);
        },
      });
    }

    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [code, pathname, navigate]);

  return <Loading>👻</Loading>;
};

export default Redirect;
