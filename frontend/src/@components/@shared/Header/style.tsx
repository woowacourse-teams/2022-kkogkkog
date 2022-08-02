import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;
  height: 54px;

  position: sticky;
  top: 0;

  background-color: ${({ theme }) => theme.colors.white_100};

  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 0 10px;

  color: ${({ theme }) => theme.colors.primary_400};
  font-weight: bold;

  border-radius: 4px 4px 0 0;

  ${({ theme }) => css`
    z-index: ${theme.layers.header};
  `}
`;

export const Logo = styled.div``;

export const Title = styled.div`
  font-size: 16px;
  padding-left: 12px;

  flex: 1;
`;

export const Profile = styled.div``;
