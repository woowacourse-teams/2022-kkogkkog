import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;
  height: 200px;
  padding: 24px 16px;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const Title = styled.p`
  font-weight: 600;
  margin-bottom: 20px;
`;

export const Description = styled.p`
  font-size: 14px;
  margin-bottom: 20px;

  ${({ theme }) => css`
    color: ${theme.colors.dark_grey_100};
  `}
`;

export const SlackAddButtonInner = styled.div`
  width: 100%;
  text-align: center;
`;
