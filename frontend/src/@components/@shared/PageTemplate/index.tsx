import { PropsWithChildren, useEffect } from 'react';

import Header from '@/@components/@shared/Header';
import PrevPathMemoization from '@/@components/@shared/PrevPathMemoization';

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
    <PrevPathMemoization>
      <Styled.Root>
        <Styled.Container>
          {hasHeader && <Header title={title} />}
          {children}
        </Styled.Container>
      </Styled.Root>
    </PrevPathMemoization>
  );
};

PageTemplate.LandingPage = function LandingPageTemplate(
  props: PropsWithChildren<PageTemplateProps>
) {
  const { hasHeader = true, children } = props;

  useEffect(() => {
    document.title = '꼭꼭 | 쿠폰으로 전하는 약속, 꼭꼭으로 간편하게';
  }, []);

  return (
    <PrevPathMemoization>
      <Styled.Root>
        <Styled.Container>
          {hasHeader && <Header css={Styled.ExtendedHeader} />}
          {children}
        </Styled.Container>
      </Styled.Root>
    </PrevPathMemoization>
  );
};

export default PageTemplate;
