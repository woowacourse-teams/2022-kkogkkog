import { AxiosError } from 'axios';
import { Link } from 'react-router-dom';

import landingLogoImage from '@/assets/images/landing_logo.png';
import { PATH } from '@/Router';
import theme from '@/styles/theme';

import Icon from '../Icon';
import PageTemplate from '../PageTemplate';
import * as Styled from './style';

export interface ErrorFallbackProps {
  error: AxiosError;
  resetErrorBoundary?: () => void;
}

const ErrorFallback = ({ resetErrorBoundary }: ErrorFallbackProps) => {
  return (
    <PageTemplate.ExtendedStyleHeader title='문제가 발생했어요' hasHeader={false}>
      <Styled.Root>
        <Link to={PATH.MAIN} css={Styled.EscapeSection} onClick={resetErrorBoundary}>
          <img src={landingLogoImage} alt='로고' width={50} height={50} />
          메인으로
        </Link>
        <Styled.ResetSection onClick={resetErrorBoundary}>
          <Styled.FlexCenter>
            <Icon iconName='reload' color={theme.colors.light_grey_200} />
          </Styled.FlexCenter>
          <button>다시 불러오기</button>
        </Styled.ResetSection>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default ErrorFallback;
