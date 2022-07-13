import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useAuthenticateForm } from '@/hooks/useAuthenticateForm';
import { PATH } from '@/Router';
import theme from '@/styles/theme';

const Join = () => {
  const {
    state: { email, password, confirmPassword, name },
    changeHandler: { onChangeEmail, onChangePassword, onChangeConfirmPassword, onChangeName },
    submitHandler: { onSubmitJoinForm },
  } = useAuthenticateForm();

  return (
    <PageTemplate title='회원가입' hasHeader={false}>
      <Styled.Root>
        <Link
          to={PATH.LANDING}
          css={css`
            display: inline-flex;

            justify-content: center;
            align-items: center;

            & > img {
              width: 40px;
            }

            & > h1 {
              font-size: 20px;
              font-weight: bold;
              margin-left: 10px;
              color: ${theme.colors.primary_400};
            }
          `}
        >
          <img src='/assets/images/logo.png' alt='로고' />
          <h1>꼭꼭</h1>
        </Link>
        <Styled.FormRoot onSubmit={onSubmitJoinForm}>
          <Input
            id='email'
            type='email'
            label='이메일'
            placeholder='이메일'
            value={email}
            onChange={onChangeEmail}
          />
          <Input
            id='password'
            type='password'
            label='비밀번호'
            additionalLabel='영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요'
            placeholder='비밀번호'
            value={password}
            onChange={onChangePassword}
          />
          <Input
            id='confirm-password'
            type='password'
            label='비밀번호 확인'
            placeholder='비밀번호 확인'
            value={confirmPassword}
            onChange={onChangeConfirmPassword}
          />
          <Input
            id='name'
            type='text'
            label='닉네임'
            additionalLabel='2~8자 사이의 닉네임을 입력해주세요'
            placeholder='닉네임'
            value={name}
            onChange={onChangeName}
          />
          <Button>회원가입</Button>
        </Styled.FormRoot>
      </Styled.Root>
    </PageTemplate>
  );
};

export default Join;

const Styled = {
  Root: styled.div`
    min-height: 100vh;

    display: flex;
    flex-direction: column;
    justify-content: center;
  `,
  FormRoot: styled.form`
    display: flex;
    flex-direction: column;

    padding: 20px;

    & > div {
      margin-bottom: 15px;
    }
  `,
};
