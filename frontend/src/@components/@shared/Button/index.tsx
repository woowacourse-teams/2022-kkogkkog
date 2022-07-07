import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Button = styled.button`
  width: 100%;
  padding: 10px;

  border: none;
  border-radius: 4px;
  box-shadow: 0 4px 4px 0 #00000025;

  ${({ theme }) => css`
    background-color: ${theme.colors.primary_400};
    color: ${theme.colors.white_100};
  `}
`;

export default Button;
