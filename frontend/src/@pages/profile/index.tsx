import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import useMe from '@/@hooks/user/useMe';
import { client } from '@/apis';
import { PATH } from '@/Router';

const ProfilePage = () => {
  const navigate = useNavigate();

  const { remove } = useMe();

  const onClickLogoutButton = () => {
    client.defaults.headers['Authorization'] = '';

    localStorage.removeItem('user-token');

    remove();

    navigate(PATH.LANDING);
  };

  return (
    <PageTemplate title='회원정보수정'>
      <Styled.Root>
        <Button
          onClick={onClickLogoutButton}
          css={css`
            margin-top: 20px;
            width: 50%;
          `}
        >
          로그아웃
        </Button>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ProfilePage;

const Styled = {
  Root: styled.div`
    text-align: center;
  `,
};
