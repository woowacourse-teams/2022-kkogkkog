import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div<{ position: 'top' | 'middle' | 'bottom' }>`
  max-width: 414px;
  width: 100%;
  border-radius: 20px;
  padding: 24px;

  display: flex;
  flex-direction: column;

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
  }}
`;

export const ModalTop = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
`;
