import { AxiosError } from 'axios';

import theme from '@/styles/theme';

import Icon from '../Icon';
import * as Styled from './style';

export interface ErrorFallbackProps {
  error: AxiosError;
  resetErrorBoundary?: () => void;
}

const ErrorFallback = ({ error, resetErrorBoundary }: ErrorFallbackProps) => {
  return (
    <Styled.Root onClick={resetErrorBoundary}>
      <Icon iconName='reload' color={theme.colors.light_grey_200} />
      <button>다시 불러오기</button>
    </Styled.Root>
  );
};

export default ErrorFallback;
