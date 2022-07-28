import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  justify-content: center;
  flex-wrap: no-wrap;

  width: 100%;

  & > button {
    margin: 5px;
  }
  & > button:first-of-type {
    margin-left: 0;
  }
  & > button:last-of-type {
    margin-right: 0;
  }
`;

export const FilterButton = styled.button<{ isFocus?: boolean; horizontalScroll?: boolean }>`
  max-width: 100px;
  aspect-ratio: 1/1;

  padding: 10px;
  flex: 1;

  border-radius: 20px;

  transition: background-color 0.1s ease-in;

  ${({ theme, isFocus }) => css`
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.12);

    background-color: ${isFocus ? theme.colors.primary_400 : theme.colors.white_100};
    color: ${isFocus ? theme.colors.white_100 : theme.colors.grey_100};
  `}
`;

export const ExtendedPlaceholder = css`
  margin-right: 20px;
`;
