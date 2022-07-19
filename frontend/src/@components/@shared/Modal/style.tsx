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

export const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;

  & > div {
    padding: 0 10px;
  }

  & > div:nth-child(1) {
    padding-left: 0;
  }

  & > div:nth-last-child() {
    padding-right: 0;
  }
`;

export const ButtonInner = styled.div`
  flex: 1;
  & > button {
    height: 40px;
  }
`;
