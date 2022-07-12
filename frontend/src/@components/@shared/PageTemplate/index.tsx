import { PropsWithChildren, useEffect } from 'react';

import Header from '@/@components/@shared/Header';

import * as Styled from './style';

interface PageTemplateProps {
  title: string;
}

const PageTemplate = ({ title, children }: PropsWithChildren<PageTemplateProps>) => {
  useEffect(() => {
    document.title = `${title} | 쿠폰으로 전하는 약속, 꼭꼭으로 간편하게`;
  }, [title]);

  return (
    <Styled.Root>
      <Styled.Container>
        <Header title={title} />
        {children}
      </Styled.Container>
    </Styled.Root>
  );
};

export default PageTemplate;
