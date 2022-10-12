import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  border-radius: 4px;
`;

export const Inner = styled.div`
  padding: 0 20px 40px 20px;
`;

export const PreviewContainer = styled.div`
  display: flex;
  padding: 25px;

  height: 170px;

  overflow-x: scroll;

  & > div:not(:last-child) {
    margin-right: 20px;
  }
`;

export const GuideContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;

  ${({ theme }) => css`
    color: ${theme.colors.light_grey_200};

    box-shadow: ${theme.shadow.type_2};

    border-radius: 20px;
  `}
`;
