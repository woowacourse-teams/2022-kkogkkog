import { ChangeEvent, FormEvent, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { createKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import {
  KKOGKKOG_COLORS,
  KKOGKKOG_ENG_TYPE,
  KKOGKKOG_MODIFIERS,
  kkogkkogColors,
  kkogkkogModifiers,
  kkogkkogType,
} from '@/types/client/kkogkkog';
import { UserResponse } from '@/types/remote/response';

export const useKkogKkogForm = () => {
  const navigate = useNavigate();

  const [receiverList, setReceiverList] = useState<UserResponse[]>([]);
  const [couponType, setCouponType] = useState<KKOGKKOG_ENG_TYPE>(kkogkkogType[0].engType);
  const [modifier, setModifier] = useState<KKOGKKOG_MODIFIERS>(kkogkkogModifiers[0]);
  const [color, setColor] = useState<KKOGKKOG_COLORS>(kkogkkogColors[0]);
  const [message, setMessage] = useState('');

  /** Add Typing */
  const { mutate: createKkogKKogMutate } = useMutation(createKkogkkog, {
    onSuccess() {
      navigate(PATH.LANDING, {
        state: {
          action: 'create',
        },
      });
    },
    onError() {
      alert('입력창을 확인하고 다시 시도해주세요.');
    },
  });

  const onSelectType = (type: KKOGKKOG_ENG_TYPE) => {
    setCouponType(type);
  };

  const onSelectModifier = (modifier: KKOGKKOG_MODIFIERS) => {
    setModifier(modifier);
  };

  const onSelectColor = (color: KKOGKKOG_COLORS) => {
    setColor(color);
  };

  const onChangeMessage = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value },
    } = e;

    setMessage(value);
  };

  const onSelectReceiver = (user: UserResponse) => {
    const isSelected = receiverList.some(receiver => receiver.id === user.id);

    if (isSelected) {
      setReceiverList(prev => prev.filter(({ id: receiverId }) => receiverId !== user.id));

      return;
    }

    setReceiverList(prev => [...prev, user]);
  };

  const onSubmitCreateForm = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (receiverList.length === 0) {
      alert('정보를 모두 입력해주세요 !');

      return;
    }

    createKkogKKogMutate({
      receivers: receiverList.map(({ id }) => id),
      backgroundColor: color,
      modifier,
      message,
      couponType,
    });
  };

  return {
    state: {
      receiverList,
      couponType,
      modifier,
      color,
      message,
    },
    changeHandler: {
      onSelectReceiver,
      onSelectType,
      onSelectModifier,
      onSelectColor,
      onChangeMessage,
    },
    submitHandler: {
      create: onSubmitCreateForm,
    },
  };
};
