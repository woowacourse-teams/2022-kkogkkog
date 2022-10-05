import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useAuthenticateForm } from '@/@hooks/ui/user/useAuthenticateForm';
import logoImage from '@/assets/images/logo.png';
import { PATH } from '@/Router';

const JoinPage = () => {
  const {
    state: { name },
    changeHandler: { onChangeName },
    submitHandler: { join: onSubmitForm },
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
          `}
        >
          <img src={logoImage} alt='로고' width={40} height={40} />
          <Styled.BrandName>꼭꼭</Styled.BrandName>
        </Link>
        <Styled.FormRoot onSubmit={onSubmitForm}>
          <Input
            id='name'
            type='text'
            label='닉네임'
            description='1~6자 사이의 닉네임을 입력해주세요'
            placeholder='닉네임'
            value={name}
            onChange={onChangeName}
            maxLength={6}
          />
          <Button>회원가입</Button>
        </Styled.FormRoot>
      </Styled.Root>
    </PageTemplate>
  );
};

export default JoinPage;

const Styled = {
  Root: styled.div`
    min-height: calc(var(--vh, 1vh) * 100);

    display: flex;
    flex-direction: column;
    justify-content: center;

    gap: 20px;
  `,
  FormRoot: styled.form`
    display: flex;
    flex-direction: column;

    padding: 20px;
    margin: 10px;

    gap: 10px;

    & > div {
      margin-bottom: 15px;
    }
  `,
  BrandName: styled.h1`
    font-size: 20px;
    font-weight: bold;
    margin-left: 10px;
    color: ${({ theme }) => theme.colors.primary_400};
  `,
};
