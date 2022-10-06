import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  min-height: calc(var(--vh, 1vh) * 90);

  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Container = styled.div`
  width: 100%;

  padding: 40px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 30px;
`;

export const LinkButton = (theme: Theme) => css`
  width: 100%;
  height: 120px;

  padding: 10px;

  color: ${theme.colors.white_100};
  background-color: ${theme.colors.primary_400};
  border-radius: 20px;

  display: flex;
  justify-content: center;
  align-items: center;

  font-weight: bold;
`;
