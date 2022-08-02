import { ChangeEventHandler, FormEventHandler, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import PageTemplate from '@/@components/@shared/PageTemplate';
import { useEditMeMutation, useMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

import * as Styled from './style';

const ProfileEditPage = () => {
  const navigate = useNavigate();
  const [nicknameOnEdit, setNicknameOnEdit] = useState('');

  const { data: me } = useMe();
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

    if (nickname === nicknameOnEdit) {
      navigate(PATH.PROFILE);

      return;
    }

    editMeMutation.mutate(
      { nickname: nicknameOnEdit },
      {
        onSuccess() {
          navigate(PATH.PROFILE);
        },
      }
    );
  };

  if (!me) return <></>;

  const { nickname, imageUrl } = me;

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
