import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;

  overflow-x: scroll;
  overflow-y: hidden;
`;

export const NoneContentsContainer = styled.div`
  width: 100%;
  height: 200px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  gap: 10px;

  & > h2 {
    font-size: 50px;
  }

  & > h3 {
    font-size: 18px;

    ${({ theme }) => css`
      color: ${theme.colors.drak_grey_200};
    `}
  }

  & > h4 {
    font-size: 14px;

    ${({ theme }) => css`
      color: ${theme.colors.grey_100};
    `}
  }
`;

export const DateContainer = styled.div`
  width: 90%;
  height: 320px;

  display: flex;
  flex-direction: column;

  gap: 10px;

  padding: 20px;
  margin: 0 10px;

  border-radius: 20px;

  overflow-y: scroll;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_100};
  `}
`;

export const DateTitle = styled.div`
  font-size: 16px;
  font-weight: 600;

  ${({ theme }) => css`
    color: ${theme.colors.white_100};
  `}
`;

export const LeftArrow = styled.div`
  position: absolute;

  top: 50%;
  left: 0;

  transform: translateY(-50%);
`;

export const RightArrow = styled.div`
  position: absolute;

  top: 50%;
  right: 0;

  transform: rotate(180deg) translateY(50%);
`;
