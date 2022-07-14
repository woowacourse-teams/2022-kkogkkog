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

    margin-bottom: 8px;

    ${({ isShowLabel }) =>
      isShowLabel ||
      css`
        ${hideWithA11y}
      `}
  }
`;

export const Input = styled.input`
  border-radius: 4px;
  padding: 10px;
  border: 1px solid ${({ theme }) => theme.colors.light_grey_100};
  box-shadow: 0 1px 1px 0 #00000025;
`;

export const Description = styled.div`
  font-size: 12px;
  color: ${({ theme }) => theme.colors.grey_200};
  margin-bottom: 8px;
`;
