import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  min-height: 250px;
`;

export const SelectedUserListContainer = styled.div`
  display: flex;
  flex-wrap: nowrap;

  overflow-x: scroll;

  padding: 0 0 10px 0;
  margin-bottom: 5px;

  border-radius: 4px;
  cursor: pointer;

  & > div {
    display: flex;
    align-items: center;

    padding: 5px;
    margin-right: 5px;

    border-radius: 4px;
    box-shadow: 0 4px 4px 0 #00000025;

    animation: show-up 0.3s ease-in;

    cursor: pointer;
  }

  ${({ theme }) => css`
    background-color: ${theme.colors.background_0};
  `}
`;

export const SelectedUserContainer = styled.div`
  margin-right: 10px;

  padding: 5px;

  border-radius: 4px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: ${theme.colors.white_100};

    box-shadow: ${theme.shadow.type_6};
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

export const SearchContainer = styled.div`
  width: 100%;

  height: fit-content;
  max-height: 150px;

  overflow-y: scroll;

  font-size: 12px;

  margin-top: 5px;

  border-radius: 4px;

  animation: drop-down 0.2s ease-in;

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
  `}
`;

export const SearchedUser = styled.div<{ isSelected: boolean }>`
  padding: 10px;

  animation: show-up 0.3s ease-in;

  cursor: pointer;

  ${({ theme, isSelected }) => css`
    border-bottom: 1px solid ${theme.colors.light_grey_200};

    ${isSelected &&
    css`
      background-color: ${theme.colors.light_grey_100};
    `}
  `}
`;
