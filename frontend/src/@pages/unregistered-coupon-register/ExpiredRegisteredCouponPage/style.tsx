import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Root = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px;

  height: calc(var(--vh, 1vh) * 100);

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400_opacity};
  `}
`;

export const Description = styled.p`
  font-family: 'BMHANNAProOTF';
  font-size: 20px;
`;

export const ExtendedLink = css`
  width: 50%;
`;
