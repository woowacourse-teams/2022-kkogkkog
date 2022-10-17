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
  width: 100%;
  height: 132px; // 쿠폰이 있는 상황과 height가 동일해야 한다.

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  gap: 5px;

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
