import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const LoginForm = styled.form`
  width: 100%;

  & input {
    width: 100%;
    height: 50px;
    font-size: 14px;
    border: 1px solid ${({ theme }) => theme.colors.light_grey_100};

    padding: 0 10px;
  }

  & input::placeholder {
    ${({ theme }) => css`
      color: ${theme.colors.light_grey_100};
    `}
  }

  & > button {
    width: 100%;
    height: 50px;

    font-weight: bold;

    border-radius: 20px;

    margin-bottom: 10px;

    ${({ theme }) => css`
      background-color: ${theme.colors.primary_400};
      color: ${theme.colors.white_100};
    `}
  }
`;
