import { PropsWithChildren, useEffect } from 'react';

import Header from '@/@components/@shared/Header';

import * as Styled from './style';

interface PageTemplateProps {
  title: string;
}

const PageTemplate = ({ title, children }: PropsWithChildren<PageTemplateProps>) => {
  useEffect(() => {
    document.title = `${title} | 세상에 고마움을 전달하는 꼭꼭`;
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
