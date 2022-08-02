import { ChangeEventHandler, FormEventHandler, useEffect, useState } from 'react';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useEditMeMutation, useMe } from '@/@hooks/@queries/user';

import * as Styled from './style';

const ProfileEditPage = () => {
  const { data: me } = useMe();
  const [nicknameOnEdit, setNicknameOnEdit] = useState('');
  const editMeMutation = useEditMeMutation();

  useEffect(() => {
    if (me) {
      setNicknameOnEdit(me.nickname);
    }
  }, [me]);

  const onChangeNicknameOnEdit: ChangeEventHandler<HTMLInputElement> = e => {
    const {
      target: { value },
    } = e;

    setNicknameOnEdit(value);
  };

  const onSubmitEditedInfo: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();

    editMeMutation.mutate({ nickname: nicknameOnEdit });
  };

  if (!me) return <></>;

  const { id, userId, workspaceId, email, nickname, imageUrl } = me;

  return (
    <PageTemplate title='프로필 수정'>
      <Styled.Root>
        <Styled.ImageInner>
          <img src={imageUrl} alt='프사' width='86px' />
        </Styled.ImageInner>
        <Styled.Form onSubmit={onSubmitEditedInfo}>
          <Input label='닉네임' value={nicknameOnEdit} onChange={onChangeNicknameOnEdit} />
          <Styled.ButtonInner>
            <Button>완료</Button>
          </Styled.ButtonInner>
        </Styled.Form>
      </Styled.Root>
    </PageTemplate>
  );
};

export default ProfileEditPage;
