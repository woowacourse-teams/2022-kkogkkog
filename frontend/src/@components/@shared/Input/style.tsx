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
