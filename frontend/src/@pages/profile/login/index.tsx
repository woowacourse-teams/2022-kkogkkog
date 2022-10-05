import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import logoImage from '@/assets/images/logo.png';
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
          <img src={logoImage} alt='로고' width={36} height={36} />
          <Styled.BrandName>꼭꼭</Styled.BrandName>
        </Link>
        <Styled.SlackLink href='https://slack.com/openid/connect/authorize?scope=openid%20email%20profile&amp;response_type=code&amp;redirect_uri=https%3A%2F%2Fkkogkkog.com%2Flogin%2Fredirect&amp;client_id=3711114175136.3863202543751'>
          <Icon iconName='slack' size='20' />
          슬랙으로 로그인
        </Styled.SlackLink>
        <Styled.GoogleLink
          href={`https://accounts.google.com/o/oauth2/auth?client_id=722307223606-9hllknij10hdojacsmk53s1dcehd22uk.apps.googleusercontent.com&redirect_uri=${window.location.origin}/login/google/redirect&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email&access_type=offline`}
        >
          <Icon iconName='google' size='20' />
          구글로 로그인
        </Styled.GoogleLink>
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

    height: calc(var(--vh, 1vh) * 100);
  `,
  BrandName: styled.h1`
    font-size: 20px;
    font-weight: bold;

    margin-left: 16px;

    ${({ theme }) => css`
      color: ${theme.colors.primary_400};
    `}
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
    box-shadow: ${({ theme }) => theme.shadow.type_2};

    border: 0;
    border-radius: 20px;

    font-size: 16px;
    font-weight: 600;

    text-decoration: none;
  `,
  GoogleLink: styled.a`
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 12px;

    width: 100%;
    height: 48px;
    margin-bottom: 20px;

    color: ${({ theme }) => theme.colors.grey_300};
    background-color: #fff;
    box-shadow: ${({ theme }) => theme.shadow.type_2};

    border: 0;
    border-radius: 20px;

    font-size: 16px;
    font-weight: 600;

    text-decoration: none;
  `,
};
