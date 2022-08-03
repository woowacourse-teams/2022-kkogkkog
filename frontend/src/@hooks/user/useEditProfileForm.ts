import { FormEventHandler, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useEditMeMutation, useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

import useInput from '../@common/useInput';

const useEditProfileForm = () => {
  const navigate = useNavigate();

  const [nickname, setNickname, onChangeNickname] = useInput();

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
      navigate(PATH.PROFILE);

      return;
    }

    editMeMutation.mutate(
      { nickname },
      {
        onSuccess() {
          navigate(PATH.PROFILE);
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
