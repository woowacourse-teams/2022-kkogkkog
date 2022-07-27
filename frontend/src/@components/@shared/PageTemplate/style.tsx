import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;

  background-color: ${({ theme }) => theme.colors.background_3};
`;

export const LandingPageTemplateRoot = styled.div`
  width: 100%;

  border-radius: 0 0 20px 20px;
`;

export const Container = styled.div`
  min-height: 100vh;

  max-width: 414px;
  min-width: 320px;

  margin: 0 auto;

  position: relative;

  ${({ theme }) => css`
    /* box-shadow: ${theme.shadow.type_4}; */
    background-color: ${theme.colors.background_0};
  `}
`;

export const LandingPageTemplateContainer = styled.div`
  width: 100%;

  min-height: 100vh;

  max-width: 414px;
  min-width: 320px;

  margin: 0 auto;

  position: relative;
`;

export const ExtendedHeader = (theme: Theme) => css`
  position: relative;

  background-color: ${theme.colors.primary_400_opacity};
`;
