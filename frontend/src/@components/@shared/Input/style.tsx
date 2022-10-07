import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { hideWithA11y } from '@/styles/mixin';

export const Root = styled.div<{ isShowLabel?: boolean }>`
  display: flex;
  flex-direction: column;
  position: relative;

  & > label {
    display: inline-block;

    color: ${({ theme }) => theme.colors.drak_grey_200};
    font-size: 14px;
    font-weight: 600;

    margin-bottom: 8px;

    ${({ isShowLabel = true }) =>
      isShowLabel ||
      css`
        ${hideWithA11y}
      `}
  }
`;

export const Input = styled.input`
  border-radius: 10px;
  padding: 10px 12px;

  ${({ theme }) => css`
    border: 1px solid ${theme.colors.primary_200};

    &::-webkit-input-placeholder {
      color: ${theme.colors.light_grey_200};
    }
    &::-ms-input-placeholder {
      color: ${theme.colors.light_grey_200};
    }
  `}
`;

export const Description = styled.div`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.grey_200};
  margin-bottom: 8px;
`;

export const CounterInputContainer = styled.div`
  position: relative;

  display: flex;
  justify-content: center;
  align-items: center;

  ${({ theme }) => css`
    border-radius: 10px;
    border: 1px solid ${theme.colors.primary_200};

    &::-webkit-input-placeholder {
      color: ${theme.colors.light_grey_200};
    }
    &::-ms-input-placeholder {
      color: ${theme.colors.light_grey_200};
    }
  `}

  & > input[type='number'] {
    width: 100px;
    border: none;
    text-align: center;
  }
`;

export const CounterButton = styled.div`
  text-align: center;
  width: 30px;

  position: absolute;
  padding: 5px 0;

  cursor: pointer;

  border-radius: 10px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: white;
  `}
`;

export const PlusCounterButton = styled(CounterButton)`
  right: 20px;
`;

export const MinusCounterButton = styled(CounterButton)`
  left: 20px;
`;
