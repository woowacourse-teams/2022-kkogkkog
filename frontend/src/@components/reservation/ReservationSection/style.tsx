import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.ul`
  display: flex;

  overflow-x: scroll;
  overflow-y: hidden;

  gap: 15px;
`;

export const NoneContentsContainer = styled.div`
  width: 100%;
  height: 320px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  gap: 10px;
`;

export const NonContentsText1 = styled.p`
  font-size: 18px;

  ${({ theme }) => css`
    color: ${theme.colors.dark_grey_200};
  `}
`;

export const NonContentsText2 = styled.p`
  font-size: 14px;

  ${({ theme }) => css`
    color: ${theme.colors.grey_100};
  `}
`;

export const DateContainer = styled.li`
  width: 350px;
  height: 320px;

  display: flex;
  flex-direction: column;

  gap: 10px;

  padding: 20px;

  border-radius: 20px;

  overflow-y: scroll;
  overflow-x: hidden;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_100};
  `}
`;

export const DateTitle = styled.div`
  display: flex;
  justify-content: space-between;

  font-size: 18px;
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
