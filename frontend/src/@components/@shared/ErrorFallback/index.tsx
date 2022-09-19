import { AxiosError } from 'axios';

import theme from '@/styles/theme';

import Icon from '../Icon';
import PageTemplate from '../PageTemplate';
import * as Styled from './style';

export interface ErrorFallbackProps {
  error: AxiosError;
  resetErrorBoundary?: () => void;
}

const ErrorFallback = ({ error, resetErrorBoundary }: ErrorFallbackProps) => {
  return (
    <PageTemplate title='꼭꼭' hasHeader={false}>
      <Styled.Root>
        <img src='/assets/images/landing_logo.png' alt='로고' width='86' />
        <Styled.ResetSection onClick={resetErrorBoundary}>
          <Icon iconName='reload' color={theme.colors.light_grey_200} />
          <button>다시 불러오기</button>
        </Styled.ResetSection>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ErrorFallback;
