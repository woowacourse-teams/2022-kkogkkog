import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div<{ backgroundColor: string }>`
  padding: 3px 5px;
  border-radius: 20px;

  font-size: 12px;

  ${({ theme, backgroundColor }) => css`
    color: ${theme.colors.white_100};
    background-color: ${backgroundColor};
  `}
`;
