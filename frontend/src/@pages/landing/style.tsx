import type { Theme } from '@emotion/react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  background-color: ${({ theme }) => theme.colors.white_100};
  border-radius: 4px;
`;

export const UnAuthorizedRoot = styled.div`
  min-height: calc(var(--vh, 1vh) * 100);
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const UnAuthorizedContainer = styled.div`
  display: flex;

  flex-direction: column;
  justify-content: center;
  align-items: center;

  text-align: center;
  font-weight: 600;

  gap: 20px;
`;

export const ExtraBold = styled.span`
  font-weight: 800;
`;

export const AdditionalExplanation = styled.div`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.primary_400};

  margin-top: 10px;
`;
