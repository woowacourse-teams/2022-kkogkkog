import { Link, useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useToast } from '@/@hooks/@common/useToast';
import { useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

import * as Styled from './style';

const ProfilePage = () => {
  const navigate = useNavigate();

  const { me, logout } = useFetchMe();
  const { displayMessage } = useToast();

  const onClickLogoutButton = () => {
    logout();

    navigate(PATH.LANDING);
    displayMessage('로그아웃 되었습니다.', false);
  };

  return (
    <PageTemplate title='프로필'>
      <Styled.Root>
        <Styled.MeInfo>
          <Styled.ProfileImage src={me?.imageUrl} alt='프로필' width={51} height={51} />
          <Styled.Nickname>{me?.nickname}</Styled.Nickname>
        </Styled.MeInfo>
        <Link to={PATH.PROFILE_EDIT}>
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
