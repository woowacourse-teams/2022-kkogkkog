import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;

  height: calc(var(--vh, 1vh) * 100);

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const EscapeSection = (theme: Theme) => css`
  width: 100px;
  height: 80px;

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  gap: 12px;

  color: ${theme.colors.primary_400};

  cursor: pointer;

  & > button {
    font-size: 16px;
  }
`;

export const ResetSection = styled.div`
  width: 100px;
  height: 80px;

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  color: ${({ theme }) => theme.colors.light_grey_200};

  cursor: pointer;

  & > button {
    font-size: 16px;
  }
`;

export const FlexCenter = styled.div`
  width: 50px;
  height: 50px;

  display: flex;
  justify-content: center;
  align-items: center;
`;
