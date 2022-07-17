import { ChangeEvent, FormEvent, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { createKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import {
  KKOGKKOG_COLORS,
  kkogkkog_colors,
  KKOGKKOG_KOREAN_TYPE,
  KKOGKKOG_MODIFIERS,
  kkogkkog_modifiers,
  kkogkkog_type,
  User,
} from '@/types/domain';

export const useKkogKkogForm = () => {
  const navigate = useNavigate();

  const [receiverList, setReceiverList] = useState<User[]>([]);
  const [couponType, setCouponType] = useState<KKOGKKOG_KOREAN_TYPE>('커피');
  const [modifier, setModifier] = useState<KKOGKKOG_MODIFIERS>(kkogkkog_modifiers[0]);
  const [color, setColor] = useState<KKOGKKOG_COLORS>(kkogkkog_colors[0]);
  const [message, setMessage] = useState('');

  const { mutate: createKkogKKogMutate } = useMutation(createKkogkkog, {
    onSuccess() {
      navigate(PATH.KKOGKKOG_LIST);
    },
  });

  const onSelectType = (type: KKOGKKOG_KOREAN_TYPE) => {
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

  const onSelectReceiver = (user: User) => {
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
      couponType:
        kkogkkog_type[kkogkkog_type.findIndex(type => type.koreanType === couponType)].engType,
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
