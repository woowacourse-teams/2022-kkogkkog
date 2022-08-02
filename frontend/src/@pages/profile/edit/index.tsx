import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useMe } from '@/@hooks/@queries/user';
import { useAuthenticateForm } from '@/@hooks/user/useAuthenticateForm';

import * as Styled from './style';

const ProfileEditPage = () => {
  const {
    state: { name },
    changeHandler: { onChangeName },
    submitHandler: { edit: onSubmitEditedForm },
  } = useAuthenticateForm();

  const { data: me } = useMe();

  return (
    <PageTemplate title='프로필 수정'>
      <Styled.Root>
        <Styled.ImageInner>
          <img src={me?.imageUrl} alt='프사' width='86px' />
        </Styled.ImageInner>
        <Styled.Form onSubmit={onSubmitEditedForm}>
          <Input label='닉네임' value={name} onChange={onChangeName} />
          <Styled.ButtonInner>
            <Button>완료</Button>
          </Styled.ButtonInner>
        </Styled.Form>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ProfileEditPage;
