import PageTemplate from '@/@components/@shared/PageTemplate';

import * as Styled from './style';

const NotFoundPage = () => {
  return (
    <PageTemplate title='없는 페이지'>
      <Styled.Root>
        <img src='/assets/images/404.png' alt='로고' />
        <h2>찾을 수 없는 페이지에요.</h2>
        <h3>요청하신 페이지가 사라졌거나, 잘못된 경로로 이동하셨어요 :)</h3>
      </Styled.Root>
    </PageTemplate>
  );
};

export default NotFoundPage;
