import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  & > * + * {
    margin-top: 20px;
  }
`;

export const TextContainer = styled.div<{ fontSize?: string }>`
  width: 100%;
  min-height: 400px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  gap: 10px;

  & > h2 {
    font-size: 16px;
  }

  & > h3 {
    font-size: 14px;
  }

  ${({ theme, fontSize }) => css`
    color: ${theme.colors.grey_100};
    font-size: ${fontSize};
  `}
`;
