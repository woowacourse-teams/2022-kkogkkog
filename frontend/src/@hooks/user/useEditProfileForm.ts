import { FormEventHandler, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useEditMeMutation, useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

const useEditProfileForm = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

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
      navigate(PATH.PROFILE, { replace: true });

      return;
    }

    if (!nickname.match(/^[가-힣a-zA-Z0-9]{1,6}$/)) {
      displayMessage('잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 1~6글자)', true);

      return;
    }

    editMeMutation.mutate(
      { nickname },
      {
        onSuccess() {
          displayMessage('닉네임이 변경되었습니다', false);

          navigate(PATH.PROFILE, { replace: true });
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
