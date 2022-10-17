import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div``;

export const TimeContainer = styled.div`
  display: flex;

  justify-content: center;
  align-items: center;
  gap: 10px;

  font-size: 24px;
  font-weight: bold;

  ${({ theme }) => css`
    color: ${theme.colors.primary_500};
  `}
`;

export const Text = styled.div`
  text-align: center;
  font-size: 16px;

  ${({ theme }) => css`
    color: ${theme.colors.primary_500}90;
  `}
`;
