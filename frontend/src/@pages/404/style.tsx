import type { Theme } from '@emotion/react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  padding: 15px;
  height: calc(var(--vh, 1vh) * 80);

  gap: 10px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  opacity: 0.8;

  & > h2 {
    font-size: 14px;
    font-weight: bold;
  }

  & > h3 {
    font-size: 12px;
  }

  ${({ theme }) => css`
    color: ${theme.colors.primary_400};
    font-size: 16px;
    font-weight: 600;
  `}
`;

export const LinkButton = (theme: Theme) => css`
  margin-top: 20px;

  width: 180px;

  background-color: ${theme.colors.primary_400};
  color: ${theme.colors.white_100};

  padding: 10px 20px;

  border-radius: 20px;

  text-align: center;
`;
