import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
  `}
`;

export const Container = styled.div`
  padding-bottom: 20px;
  margin-bottom: 10px;
`;
