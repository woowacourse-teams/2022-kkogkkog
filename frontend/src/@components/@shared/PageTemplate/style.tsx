import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;

  background-color: ${({ theme }) => theme.colors.background_3};
`;

export const Container = styled.div`
  min-height: calc(var(--vh, 1vh) * 100);

  max-width: 414px;
  min-width: 320px;

  padding-bottom: 20px;

  margin: 0 auto;

  position: relative;

  ${({ theme }) => css`
    background-color: ${theme.colors.background_0};
  `}
`;

export const ExtendedHeader = (theme: Theme) => css`
  position: relative;

  background-color: ${theme.colors.primary_400_opacity};
`;
