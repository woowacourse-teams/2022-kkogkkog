import type { Theme } from '@emotion/react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;

  align-items: center;
  gap: 10px;

  padding: 10px;

  border-radius: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};
  `}
`;

export const Bar = styled.div`
  height: 60px;

  border-radius: 20px;

  ${({ theme }) => css`
    border: 3px solid ${theme.colors.green_500};
  `}
`;

export const ImageContainer = styled.div`
  border-radius: 20px;

  & > img {
    width: 40px;
    border-radius: 10px;
  }
`;

export const TextContainer = styled.div`
  font-size: 14px;
  font-weight: bold;

  ${({ theme }) => css`
    color: ${theme.colors.grey_400};
  `}
`;

export const LinkButton = (theme: Theme) => css`
  font-size: 14px;

  padding: 6px;
  border-radius: 20px;

  cursor: pointer;

  color: ${theme.colors.green_500};
  border: 1px solid ${theme.colors.green_500};
`;
