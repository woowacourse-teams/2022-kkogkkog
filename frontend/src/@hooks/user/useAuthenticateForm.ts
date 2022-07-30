import { ChangeEvent, FormEvent, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { useToast } from '@/@hooks/@common/useToast';
import { client } from '@/apis';
import { join, login } from '@/apis/user';

import useMe from './useMe';

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

  const { displayMessage } = useToast();

  const [email, setEmail] = useState(defaultEmail);
  const [password, setPassword] = useState(defaultPassword);
  const [confirmPassword, setConfirmPassword] = useState(defaultConfirmPassword);
  const [name, setName] = useState(defaultName);

  const navigate = useNavigate();

  const { remove } = useMe();

  const { mutate: joinMutate } = useMutation(join, {
    onSuccess: () => {
      navigate('/login');
    },
    onError() {
      alert('회원가입에 실패했습니다.');
    },
  });

  const { mutate: loginMutate } = useMutation(login, {
    onSuccess: (data, variables, context) => {
      const {
        data: { accessToken },
      } = data;

      localStorage.setItem('user-token', accessToken);

      client.defaults.headers['Authorization'] = `Bearer ${accessToken}`;

      remove();

      navigate('/');
    },
    onError({
      response: {
        data: { error },
      },
    }) {
      // console.log(error);
      displayMessage(error, true);
    },
  });

  const onChangeEmail = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: emailValue },
    } = e;

    // 검증

    setEmail(emailValue);
  };

  const onChangePassword = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: passwordValue },
    } = e;

    // 검증

    setPassword(passwordValue);
  };

  const onChangeConfirmPassword = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: confirmPasswordValue },
    } = e;

    // 검증

    setConfirmPassword(confirmPasswordValue);
  };

  const onChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: nameValue },
    } = e;

    // 검증

    setName(nameValue);
  };

  const onSubmitJoinForm = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    joinMutate({
      email,
      password,
      nickname: name,
    });
  };

  const onSubmitLoginForm = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    loginMutate({
      email,
      password,
    });
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
    },
  };
};
