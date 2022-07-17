import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { colors, couponTypes, modifiers } from '@/@pages/kkogkkog-list/create';
import { createKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import { KkogKkogType, User } from '@/types/domain';

export const useKkogKkogForm = () => {
  const navigate = useNavigate();

  const [receiverList, setReceiverList] = useState<User[]>([]);
  const [couponType, setCouponType] = useState<KkogKkogType>(couponTypes[0].type);
  const [modifier, setModifier] = useState<typeof modifiers[number]>(modifiers[0]);
  const [color, setColor] = useState<typeof colors[number]>(colors[0]);
  const [message, setMessage] = useState('');

  const { mutate: createKkogKKogMutate } = useMutation(createKkogkkog, {
    onSuccess() {
      navigate(PATH.KKOGKKOG_LIST);
    },
  });

  const onSelectType = (type: KkogKkogType) => {
    setCouponType(type);
  };

  const onSelectModifier = (modifier: typeof modifiers[number]) => {
    setModifier(modifier);
  };

  const onSelectColor = (color: typeof colors[number]) => {
    setColor(color);
  };

  const onChangeMessage = e => {
    const {
      target: { value },
    } = e;

    setMessage(value);
  };

  const onSelectReceiver = user => {
    const isSelected = receiverList.some(receiver => receiver.id === user.id);

    if (isSelected) {
      setReceiverList(prev => prev.filter(({ id: receiverId }) => receiverId !== user.id));

      return;
    }

    setReceiverList(prev => [...prev, user]);
  };

  const onSubmitCreateForm = e => {
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
