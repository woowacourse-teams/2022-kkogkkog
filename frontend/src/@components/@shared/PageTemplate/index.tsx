import { PropsWithChildren, useEffect } from 'react';

import Header from '@/@components/@shared/Header';

import * as Styled from './style';

interface PageTemplateProps {
  title: string;
  hasHeader?: boolean;
}

const PageTemplate = (props: PropsWithChildren<PageTemplateProps>) => {
  const { title, hasHeader = true, children } = props;

  useEffect(() => {
    document.title = `${title || '꼭꼭'} | 쿠폰으로 전하는 약속, 꼭꼭으로 간편하게`;
  }, [title]);

  return (
    <Styled.Root>
      <Styled.Container>
        {hasHeader && <Header title={title} />}
        {children}
      </Styled.Container>
    </Styled.Root>
  );
};

export default PageTemplate;
