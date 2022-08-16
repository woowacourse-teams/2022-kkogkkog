import { FormEventHandler, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useEditMeMutation, useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

const useEditProfileForm = ({ type }: { type: 'join' | null }) => {
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
      navigate(PATH[type === 'join' ? 'LANDING' : 'PROFILE'], { replace: true });

      return;
    }

    editMeMutation.mutate(
      { nickname },
      {
        onSuccess() {
          navigate(PATH[type === 'join' ? 'LANDING' : 'PROFILE'], { replace: true });
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
