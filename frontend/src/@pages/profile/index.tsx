import React from 'react';
import { useNavigate } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import { client } from '@/apis';
import useMe from '@/hooks/useMe';
import { PATH } from '@/Router';

const ProfilePage = () => {
  const navigate = useNavigate();

  const { remove } = useMe();

  const onClickLogoutButton = () => {
    client.defaults.headers['Authorization'] = undefined;

    localStorage.removeItem('user-token');

    remove();

    navigate(PATH.LANDING);
  };

  return (
    <PageTemplate title='회원정보수정'>
      <button onClick={onClickLogoutButton}>logout</button>
    </PageTemplate>
  );
};

export default ProfilePage;
