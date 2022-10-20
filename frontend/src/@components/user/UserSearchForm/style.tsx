import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  min-height: 250px;

  padding: 8px 0;
`;

export const SelectedUserListContainer = styled.div`
  display: flex;
  flex-wrap: nowrap;

  overflow-x: scroll;

  padding: 0 0 10px 0;
  margin-bottom: 5px;

  border-radius: 4px;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_0};
  `}
`;

export const SelectedUserContainer = styled.div`
  font-size: 12px;

  display: flex;
  align-items: center;

  padding: 6px 9px;
  margin-right: 5px;

  animation: show-up 0.3s ease-in;

  cursor: pointer;
  margin-right: 10px;

  border-radius: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: ${theme.colors.white_100};
  `}
`;

export const InputContainer = styled.div`
  & input {
    padding-right: 30px;
  }
`;

export const CloseButton = styled.div`
  right: 10px;
  top: 50%;

  transform: translateY(-50%);

  cursor: pointer;
`;

export const SearchedUserContainer = styled.div`
  width: 100%;

  height: fit-content;
  max-height: 150px;

  overflow-y: scroll;

  font-size: 12px;

  margin-top: 5px;

  border-radius: 10px;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_0};
    border: 1px solid ${theme.colors.light_grey_100};
  `}
`;

export const TextContainer = styled.div`
  width: 100%;

  height: 100%;
  min-height: 150px;

  font-size: 14px;

  display: flex;
  justify-content: center;
  align-items: center;

  ${({ theme }) => css`
    color: ${theme.colors.grey_200};

    & > div + div {
      border-top: 1px solid ${theme.colors.light_grey_200};
    }
  `}
`;

export const SearchedUser = styled.button<{ isSelected: boolean }>`
  width: 100%;

  display: flex;
  align-items: center;

  font-weight: 600;
  font-size: 14px;

  padding: 10px;

  animation: show-up 0.3s ease-in;

  cursor: pointer;

  ${({ theme, isSelected }) =>
    isSelected &&
    css`
      background-color: ${theme.colors.light_grey_100};
    `}
`;

export const ProfileImage = styled.img`
  border-radius: 4px;
  margin-right: 8px;
`;

export const Email = styled.span`
  font-weight: 400;
`;
