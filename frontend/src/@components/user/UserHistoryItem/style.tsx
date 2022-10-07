import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.li<{ isRead: boolean }>`
  display: flex;

  padding: 21px 19px;

  gap: 18px;

  ${({ theme, isRead }) => css`
    background-color: ${isRead ? theme.colors.background_4 : `${theme.colors.primary_100}20`};
    cursor: pointer;
  `}
`;

export const ProfileImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  & > img {
    border-radius: 50%;
  }
`;

export const InformationContainer = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;
`;

export const Contents = styled.div`
  font-size: 12px;
`;
export const Date = styled.div`
  font-size: 12px;

  ${({ theme }) => css`
    color: ${theme.colors.grey_200};
  `}
`;
