import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  ${({ theme }) => css`
    background-color: ${theme.colors.background_3};
  `}
`;

export const Container = styled.div`
  padding-bottom: 20px;
  margin-bottom: 10px;

  ${({ theme }) => css`
    background-color: ${theme.colors.white_100};

    box-shadow: ${theme.shadow.type_1};
  `}

  ${({ theme }) => css`
    & > div:first-of-child {
      padding: 10px;
      text-align: center;

      color: ${theme.colors.drak_grey_200};

      & > span {
        font-weight: bold;
      }
    }
  `}
`;
