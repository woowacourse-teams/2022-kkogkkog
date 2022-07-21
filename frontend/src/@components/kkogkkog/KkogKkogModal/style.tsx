import { css } from '@emotion/react';
import styled from '@emotion/styled';

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

  & > button {
    margin: 0 10px;
  }

  & > button:nth-of-type(1) {
    margin-left: 0;
  }

  & > button:nth-last-of-type(1) {
    margin-right: 0;
  }
`;

export const ButtonInner = styled.div`
  width: 50%;

  & > button {
    height: 40px;
  }
`;

export const ExtendedButton = css`
  width: auto;
  height: 40px;
  flex: 1;
`;
