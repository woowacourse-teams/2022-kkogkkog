import { css } from '@emotion/react';
import styled from '@emotion/styled';

interface DimmedProps {
  position?: 'top' | 'middle' | 'bottom';
  backgroundColor?: string;
}

export const Dimmed = styled.div<DimmedProps>`
  width: 100vw;
  height: calc(var(--vh, 1vh) * 100);

  position: fixed;
  top: 0;
  left: 0;

  display: flex;
  justify-content: center;

  ${({ theme, backgroundColor }) => css`
    z-index: ${theme.layers.dimmed};
    background-color: ${backgroundColor || 'rgba(0, 0, 0, 0.5)'};
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

export default Dimmed;
