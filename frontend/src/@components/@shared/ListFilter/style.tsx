import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;

  width: 100%;

  padding: 10px;

  flex-wrap: no-wrap;

  overflow-x: scroll;
`;

export const FilterButton = styled.button<{ isFocus?: boolean }>`
  width: 100%;
  max-width: 100px;

  padding: 10px;
  margin: 5px;

  border-radius: 4px;

  transition: background-color 0.1s ease-in;

  ${({ theme, isFocus }) => css`
    box-shadow: ${theme.shadow.type_3};

    background-color: ${isFocus ? theme.colors.primary_400 : theme.colors.white_100};
    color: ${isFocus ? theme.colors.white_100 : theme.colors.grey_100};
  `}
`;
