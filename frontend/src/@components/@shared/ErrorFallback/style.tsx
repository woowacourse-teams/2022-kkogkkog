import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px;

  height: calc(var(--vh, 1vh) * 100);

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const ResetSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;

  color: ${({ theme }) => theme.colors.light_grey_200};
  cursor: pointer;

  & > button {
    font-size: 20px;
  }
`;
