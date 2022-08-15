import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div<{ hasCursor?: boolean }>`
  width: 140px;

  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  box-shadow: 4px 4px 20px 0px #160f3514;
  border-radius: 20px;

  padding: 12px;

  gap: 10px;

  ${({ hasCursor = true }) =>
    hasCursor &&
    css`
      cursor: pointer;
    `}
`;

export const TextContainer = styled.div`
  font-weight: 600;

  font-size: 14px;
`;

export const Preposition = styled.span`
  font-family: BMHANNAProOTF;

  ${({ theme }) => css`
    color: ${theme.colors.primary_400};
  `}
`;
