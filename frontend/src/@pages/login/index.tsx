import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import MockLoginForm from '@/@components/user/MockLoginFrm';
import { PATH } from '@/Router';

const LoginPage = () => {
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
        {process.env.NODE_ENV === undefined && <MockLoginForm />}
        <Styled.SlackLink href='https://slack.com/openid/connect/authorize?scope=openid%20email%20profile&amp;response_type=code&amp;redirect_uri=https%3A%2F%2Fkkogkkog.com%2Flogin%2Fredirect&amp;client_id=3711114175136.3863202543751'>
          <Icon iconName='slack' size='20' />
          슬랙으로 로그인
        </Styled.SlackLink>
        <Link to={PATH.JOIN}>회원가입</Link>
      </Styled.Root>
    </PageTemplate>
  );
};

export default LoginPage;

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

      border-radius: 20px;

      margin-bottom: 10px;

      ${({ theme }) => css`
        background-color: ${theme.colors.primary_400};
        color: ${theme.colors.white_100};
      `}
    }
  `,
  SlackLink: styled.a`
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 12px;

    width: 100%;
    height: 48px;
    margin-bottom: 20px;

    color: #fff;
    background-color: #4a154b;

    border: 0;
    border-radius: 20px;

    font-size: 16px;
    font-weight: 600;

    text-decoration: none;
  `,
};
