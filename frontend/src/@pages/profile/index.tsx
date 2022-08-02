import { Link, useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useFetchMe } from '@/@hooks/@queries/user';
import { client } from '@/apis';
import { PATH } from '@/Router';

import * as Styled from './style';

const ProfilePage = () => {
  const navigate = useNavigate();

  const { me, remove } = useFetchMe();

  const onClickLogoutButton = () => {
    client.defaults.headers['Authorization'] = '';

    localStorage.removeItem('user-token');

    remove();

    navigate(PATH.LANDING);
  };

  if (!me) return <></>;

  const { nickname, imageUrl } = me;

  return (
    <PageTemplate title='프로필'>
      <Styled.Root>
        <Styled.MeInfo>
          <Styled.ProfileImage src={imageUrl} width='51px' alt='프사' />
          <Styled.NickName>{nickname}</Styled.NickName>
        </Styled.MeInfo>
        <Link to={PATH.PROFILE_EDIT} state={{ me }}>
          <Button>프로필 수정</Button>
        </Link>
        <Styled.ButtonInner>
          <button onClick={onClickLogoutButton}>로그아웃</button>
        </Styled.ButtonInner>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ProfilePage;
