import { ChangeEvent, FormEvent, FormEventHandler, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { useEditMeMutation, useFetchMe } from '@/@hooks/@queries/user';
import { PATH } from '@/Router';

import { useJoinMutation, useLoginMutation } from '../@queries/user';

type UseAuthenticateFormProps = {
  defaultEmail?: string;
  defaultPassword?: string;
  defaultConfirmPassword?: string;
  defaultName?: string;
};

export const useAuthenticateForm = (props: UseAuthenticateFormProps = {}) => {
  const {
    defaultEmail = '',
    defaultPassword = '',
    defaultConfirmPassword = '',
    defaultName = '',
  } = props;

  const navigate = useNavigate();

  const [email, setEmail] = useState(defaultEmail);
  const [password, setPassword] = useState(defaultPassword);
  const [confirmPassword, setConfirmPassword] = useState(defaultConfirmPassword);
  const [name, setName] = useState(defaultName);

  const { me } = useFetchMe();

  const joinMutate = useJoinMutation();
  const loginMutate = useLoginMutation();
  const editMeMutation = useEditMeMutation();

  useEffect(() => {
    if (me) {
      setName(me.nickname);
    }
  }, [me]);

  const onChangeEmail = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: emailValue },
    } = e;

    setEmail(emailValue);
  };

  const onChangePassword = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: passwordValue },
    } = e;

    setPassword(passwordValue);
  };

  const onChangeConfirmPassword = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: confirmPasswordValue },
    } = e;

    setConfirmPassword(confirmPasswordValue);
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: nameValue },
    } = e;

    setName(nameValue);
  };

  const onSubmitJoinForm = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    joinMutate.mutate(
      {
        email,
        password,
        nickname: name,
      },
      {
        onSuccess() {
          navigate(PATH.JOIN);
        },
      }
    );
  };

  const onSubmitLoginForm = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    loginMutate.mutate(
      {
        email,
        password,
      },
      {
        onSuccess() {
          navigate(PATH.LANDING);
        },
      }
    );
  };

  const onSubmitEditedForm: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();

    if (name === me?.nickname) {
      navigate(PATH.PROFILE);

      return;
    }

    editMeMutation.mutate(
      { nickname: name },
      {
        onSuccess() {
          navigate(PATH.PROFILE);
        },
      }
    );
  };

  return {
    state: {
      email,
      password,
      confirmPassword,
      name,
    },
    changeHandler: {
      onChangeEmail,
      onChangePassword,
      onChangeConfirmPassword,
      onChangeName,
    },
    submitHandler: {
      join: onSubmitJoinForm,
      login: onSubmitLoginForm,
      edit: onSubmitEditedForm,
    },
  };
};
