import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  padding: 15px;
  height: 80vh;

  gap: 10px;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  opacity: 0.8;

  ${({ theme }) => css`
    color: ${theme.colors.primary_400};
    font-size: 16px;
    font-weight: 600;
  `}
`;
