import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const SlideRoot = styled.div`
  display: flex;

  flex-direction: row;

  overflow-x: scroll;

  gap: 10px;
  padding: 15px 0;
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
