import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useFetchMe } from '@/@hooks/@queries/user';
import useEditProfileForm from '@/@hooks/ui/user/useEditProfileForm';

import * as Styled from './style';

const ProfileEditPage = () => {
  // @TODO: 닉네임 설정 과 프로필 수정 페이지 분리될 시 state 제거
  const { me } = useFetchMe();
  const { nickname, onChangeNickname, onSubmitEditedForm } = useEditProfileForm();

  return (
    <PageTemplate title='프로필 수정'>
      <Styled.Root>
        <Styled.ImageInner>
          <img src={me?.imageUrl} alt='프사' width='86px' />
        </Styled.ImageInner>
        <Styled.Form onSubmit={onSubmitEditedForm}>
          <Input
            label='닉네임 (1~6자)'
            value={nickname}
            onChange={onChangeNickname}
            maxLength={6}
          />
          <Styled.ButtonInner>
            <Button>완료</Button>
          </Styled.ButtonInner>
        </Styled.Form>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ProfileEditPage;
