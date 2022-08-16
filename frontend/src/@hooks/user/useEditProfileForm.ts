import { FormEventHandler, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useEditMeMutation, useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

const useEditProfileForm = () => {
  const navigate = useNavigate();

  const [nickname, onChangeNickname, setNickname] = useInput('');

  const { me } = useFetchMe();
  const editMeMutation = useEditMeMutation();

  useEffect(() => {
    if (me) {
      setNickname(me.nickname);
    }
  }, [me, setNickname]);

  const onSubmitEditedForm: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();

    if (nickname === me?.nickname) {
      navigate(PATH.LANDING);

      return;
    }

    editMeMutation.mutate(
      { nickname },
      {
        onSuccess() {
          navigate(PATH.LANDING);
        },
      }
    );
  };

  return {
    nickname,
    onChangeNickname,
    onSubmitEditedForm,
  };
};

export default useEditProfileForm;
