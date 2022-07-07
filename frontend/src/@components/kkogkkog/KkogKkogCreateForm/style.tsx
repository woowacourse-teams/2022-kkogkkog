import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const FormRoot = styled.form`
  & > * + * {
    margin-top: 20px;
  }
`;

export const FeelOption = styled.li<{ isSelected: boolean }>`
  font-weight: bold;

  ${({ isSelected }) =>
    isSelected &&
    css`
      transform: scale(1.2);
      transition: transform 0.3s ease-in-out;
    `}
`;

export const ColorOption = styled.li<{ color: string; isSelected: boolean }>`
  width: 36px;
  height: 36px;

  border-radius: 50%;

  box-shadow: 0 4px 4px 0 #00000025;

  ${({ color }) =>
    css`
      background-color: ${color};
    `}

  ${({ isSelected }) =>
    isSelected &&
    css`
      transform: scale(1.2);
      transition: transform 0.3s ease-in-out;
    `}
`;

export const TypeOption = styled.li<{ isSelected: boolean }>`
  box-shadow: 0 4px 4px 0 #00000025;
  border-radius: 50%;

  & > img {
    width: 50px;
    height: 50px;

    border-radius: 50%;
  }

  ${({ isSelected }) =>
    isSelected &&
    css`
      transform: scale(1.2);
      transition: transform 0.2s ease-in-out;
    `}
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
`;
