import { useState } from 'react';

type UseAuthenticateFormProps = {
  defaultEmail?: string;
  defaultPassword?: string;
  defaultConfirmPassword?: string;
  defaultName?: string;
  type: 'Login' | 'Join' | 'Edit';
};

export const useAuthenticateForm = (props: UseAuthenticateFormProps) => {
  const {
    defaultEmail = '',
    defaultPassword = '',
    defaultConfirmPassword = '',
    defaultName = '',
    type,
  } = props;

  const [email, setEmail] = useState(defaultEmail);
  const [password, setPassword] = useState(defaultPassword);
  const [confirmPassword, setConfirmPassword] = useState(defaultConfirmPassword);
  const [name, setName] = useState(defaultName);

  const onChangeEmail = e => {
    const {
      target: { value: emailValue },
    } = e;

    // 검증

    setEmail(emailValue);
  };

  const onChangePassword = e => {
    const {
      target: { value: passwordValue },
    } = e;

    // 검증

    setPassword(passwordValue);
  };

  const onChangeConfirmPassword = e => {
    const {
      target: { value: confirmPasswordValue },
    } = e;

    // 검증

    setConfirmPassword(confirmPasswordValue);
  };

  const onChangeName = e => {
    const {
      target: { value: nameValue },
    } = e;

    // 검증

    setName(nameValue);
  };

  const onSubmitForm = e => {
    e.preventDefault();

    switch (type) {
      case 'Join':
        break;
      case 'Login':
        break;
      case 'Edit':
        break;

      default:
        break;
    }
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
    onSubmitForm,
  };
};
