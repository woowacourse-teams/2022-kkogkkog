import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Redirect from '@/@components/@shared/Redirect';
import useGetSearchParam from '@/@hooks/@common/useGetSearchParams';
import { useAddSlackApp } from '@/@hooks/business/user';

const SlackDownloadRedirect = () => {
  const code = useGetSearchParam('code');

  const { addSlackAppRedirect } = useAddSlackApp();

  useEffect(() => {
    if (!code) {
      return;
    }

    addSlackAppRedirect(code);
    // mutate가 실행된 후 해당 컴포넌트가 리렌더링 되기 때문에 dependency에 loginMutate를 넣으면 무한 렌더링이 발생함.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <Redirect code={code} />;
};

export default SlackDownloadRedirect;
