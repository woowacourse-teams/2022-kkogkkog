import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div<{ horizontalScroll?: boolean }>`
  display: flex;
  flex-wrap: no-wrap;

  width: 100%;

  padding: 10px;

  ${({ horizontalScroll }) =>
    horizontalScroll
      ? css`
          overflow-x: scroll;
        `
      : css`
          justify-content: center;
        `}
`;

export const FilterButton = styled.button<{ isFocus?: boolean; horizontalScroll?: boolean }>`
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

  ${({ horizontalScroll }) =>
    horizontalScroll
      ? css`
          width: 100%;
        `
      : css`
          flex: 1;
        `}
`;

export const ExtendedPlaceholder = css`
  margin-right: 20px;
`;
