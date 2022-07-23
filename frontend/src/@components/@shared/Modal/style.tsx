import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ANIMATION_DURATION } from '@/constants/animation';

export const Root = styled.div<{ position: 'top' | 'middle' | 'bottom'; animation?: boolean }>`
  max-width: 414px;
  width: 100%;
  border-radius: 20px;
  padding: 24px;

  display: flex;
  flex-direction: column;

  @keyframes slideUp {
    0% {
      transform: translateY(50%);
    }
    100% {
      transform: translateY(0);
    }
  }

  @keyframes slideDown {
    0% {
      transform: translateY(0);
    }
    100% {
      transform: translateY(100%);
    }
  }

  ${({ animation }) =>
    animation !== undefined && animation === true
      ? css`
          animation: slideDown ${ANIMATION_DURATION.modal}ms ease-out;
          animation-fill-mode: forwards;
        `
      : css`
          animation: slideUp ${ANIMATION_DURATION.modal}ms ease-out;
          animation-fill-mode: forwards;

          animation: name duration timing-function delay iteration-count direction fill-mode;
        `}

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};
  `}
  ${({ position }) => {
    switch (position) {
      case 'top':
        return css`
          border-radius: 0 0 20px 20px;
        `;
      case 'middle':
        return css`
          border-radius: 20px;
        `;
      case 'bottom':
        return css`
          border-radius: 20px 20px 0 0;
        `;
      default:
        break;
    }
  }};
`;

export const ModalTop = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
`;
