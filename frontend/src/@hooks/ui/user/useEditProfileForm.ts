import { FormEventHandler, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useFetchMe } from '@/@hooks/@queries/user';
import { useEditMe } from '@/@hooks/business/user';
import { PATH } from '@/Router';
import { nicknameRegularExpression } from '@/utils/regularExpression';

const useEditProfileForm = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const [nickname, onChangeNickname, setNickname] = useInput('');

  const { me } = useFetchMe();

  const { editMe } = useEditMe();

  useEffect(() => {
    if (me) {
      setNickname(me.nickname);
    }
  }, [me, setNickname]);

  const onSubmitEditedForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    if (nickname === me?.nickname) {
      return;
    }

    if (!nickname.match(nicknameRegularExpression)) {
      displayMessage('잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 1~6글자)', true);

      return;
    }

    try {
      await editMe({ nickname });

      navigate(PATH.PROFILE, { replace: true });
    } catch (error) {
      console.error(error);
    }
  };

  return {
    nickname,
    onChangeNickname,
    onSubmitEditedForm,
  };
};

export default useEditProfileForm;
