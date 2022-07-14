import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ModalTop = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;
`;

export const Message = styled.div`
  width: 100%;
  max-width: 380px;
  min-width: 125px;

  display: flex;
  justify-content: space-between;
  align-items: center;

  padding-left: 15px;

  border-radius: 4px;
  box-shadow: 0 4px 4px 0 #00000025;

  aspect-ratio: 3/1;

  cursor: pointer;
  margin-bottom: 16px;

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};
  `}
`;

export const ButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

export const ButtonInner = styled.div`
  width: 50%;

  & > button {
    height: 40px;
  }
`;
