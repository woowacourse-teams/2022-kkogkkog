import { PropsWithChildren } from 'react';

import Header from '@/@components/@shared/Header';

import * as Styled from './style';

interface PageTemplateProps {
  title: string;
}

const PageTemplate = ({ title, children }: PropsWithChildren<PageTemplateProps>) => {
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
