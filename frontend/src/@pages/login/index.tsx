import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useAuthenticateForm } from '@/hooks/useAuthenticateForm';
import { PATH } from '@/Router';

const Login = () => {
  const {
    state: { email, password },
    changeHandler: { onChangeEmail, onChangePassword },
    onSubmitForm,
  } = useAuthenticateForm({ type: 'Login' });

  return (
    <PageTemplate title='로그인' hasHeader={false}>
      <Styled.Root>
        <Link
          to={PATH.LANDING}
          css={css`
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 50px;
          `}
        >
          <img src='/assets/images/logo.png' alt='로고' width='36' />
          <Styled.BrandName>꼭꼭</Styled.BrandName>
        </Link>
        <Styled.LoginForm onSubmit={onSubmitForm}>
          <Input
            id='email'
            type='email'
            label='이메일'
            isShowLabel={false}
            placeholder='이메일'
            value={email}
            onChange={onChangeEmail}
            css={css`
              border-radius: 4px 4px 0 0;
            `}
          />
          <Input
            id='password'
            type='password'
            label='비밀번호'
            isShowLabel={false}
            placeholder='비밀번호'
            value={password}
            onChange={onChangePassword}
            css={css`
              border-radius: 0 0 4px 4px;
              margin-bottom: 36px;
            `}
          />
          <button type='submit'>로그인</button>
        </Styled.LoginForm>
        <Link to={PATH.JOIN}>회원가입</Link>
      </Styled.Root>
    </PageTemplate>
  );
};

export default Login;

const Styled = {
  Root: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0 40px;

    height: 100vh;
  `,
  BrandName: styled.h1`
    font-size: 20px;
    font-weight: bold;

    margin-left: 16px;

    ${({ theme }) => css`
      color: ${theme.colors.primary_400};
    `}
  `,
  LoginForm: styled.form`
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

      border-radius: 4px;

      margin-bottom: 20px;

      ${({ theme }) => css`
        background-color: ${theme.colors.primary_400};
        color: ${theme.colors.white_100};
      `}
    }
  `,
};
