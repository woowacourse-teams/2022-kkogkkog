import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FormRoot = styled.form`
  & > * + * {
    margin-top: 40px;
  }

  & > label {
    font-weight: 600;
  }
`;

export const CountContainer = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: ${theme.colors.white_100};
  `}
`;

export const FeelOption = styled.li<{ isSelected: boolean }>`
  font-size: 12px;
  font-weight: bold;
  opacity: 0.3;

  padding: 6px 9px;

  border-radius: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: ${theme.colors.white_100};
  `}

  ${({ isSelected }) =>
    isSelected &&
    css`
      opacity: 1;
      transition: opacity 0.2s ease-in-out;
    `}
`;

export const TypeOption = styled.li<{ isSelected: boolean }>`
  opacity: 0.3;

  ${({ isSelected }) =>
    isSelected &&
    css`
      opacity: 1;
      transition: opacity 0.2s ease-in-out;
    `}
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
`;

export const ButtonInner = styled.div`
  width: 40%;
`;

export const MessageTextarea = styled.textarea`
  width: 100%;
  height: 200px;
  border: none;
  outline: none;
  resize: none;

  margin-bottom: 10px;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
  `}
`;

export const TextareaContainer = styled.div`
  & > label {
    display: inline-block;

    color: ${({ theme }) => theme.colors.drak_grey_200};
    font-weight: 600;
    font-size: 14px;

    margin-bottom: 8px;
  }
`;

export const MessageTextareaContainer = styled.div`
  border-radius: 20px;

  padding: 20px;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
  `}
`;

export const MessageLength = styled.div`
  text-align: right;
`;
