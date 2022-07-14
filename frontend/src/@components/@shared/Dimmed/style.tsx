import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div<{ position?: 'top' | 'middle' | 'bottom' }>`
  width: 100vw;
  height: 100vh;

  position: fixed;
  top: 0;
  left: 0;

  display: flex;
  justify-content: center;

  background-color: rgba(0, 0, 0, 0.5);

  ${({ theme }) => css`
    z-index: ${theme.layers.dimmed};
  `}

  ${({ position }) => {
    switch (position) {
      case 'top':
        return css`
          align-items: flex-start;
        `;
      case 'bottom':
        return css`
          align-items: flex-end;
        `;
      default:
        return css`
          align-items: center;
        `;
    }
  }}
`;
