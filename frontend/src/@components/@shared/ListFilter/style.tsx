import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.ul`
  display: flex;
  justify-content: center;
  flex-wrap: no-wrap;

  width: 100%;

  gap: 10px;
`;

export const FilterList = styled.li`
  max-width: 120px;
  aspect-ratio: 1/1;
  flex: 1;
`;

export const FilterButton = styled.button<{ isFocus?: boolean; horizontalScroll?: boolean }>`
  width: 100%;
  height: 100%;
  padding: 5px;

  border-radius: 20px;

  transition: background-color 0.1s ease-in;

  ${({ theme, isFocus }) => css`
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.12);

    background-color: ${isFocus ? theme.colors.primary_400 : theme.colors.white_100};
    color: ${isFocus ? theme.colors.white_100 : theme.colors.grey_100};
  `}

  @media (max-width: 400px) {
    font-size: 16px;
  }

  @media (max-width: 360px) {
    font-size: 14px;
  }
`;

export const ExtendedPlaceholder = css`
  margin-right: 20px;
`;
