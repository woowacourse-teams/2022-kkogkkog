import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  & > div + div {
    margin-top: 20px;
  }
`;

export const TextContainer = styled.div<{ fontSize?: string }>`
  display: flex;
  justify-content: center;
  align-items: center;

  ${({ theme, fontSize }) => css`
    color: ${theme.colors.grey_100};
    font-size: ${fontSize};
  `}
`;
