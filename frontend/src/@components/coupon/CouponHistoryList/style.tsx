import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  ${({ theme }) => css`
    & > div + div {
      border-top: 1px solid ${theme.colors.light_grey_100};
    }
  `}
`;
