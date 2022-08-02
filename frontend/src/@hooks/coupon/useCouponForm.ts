import { ChangeEvent, FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { PATH } from '@/Router';
import {
  COUPON_COLORS,
  COUPON_ENG_TYPE,
  COUPON_MODIFIERS,
  couponColors,
  couponModifiers,
  couponTypeCollection,
} from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';

import { useCreateCouponMutation } from '../@queries/coupon';

export const useCouponForm = () => {
  const navigate = useNavigate();

  const [receiverList, setReceiverList] = useState<UserResponse[]>([]);
  const [type, setType] = useState<COUPON_ENG_TYPE>(couponTypeCollection[0].engType);
  const [modifier, setModifier] = useState<COUPON_MODIFIERS>(couponModifiers[0]);
  const [color, setColor] = useState<COUPON_COLORS>(couponColors[0]);
  const [message, setMessage] = useState('');

  const createCouponMutate = useCreateCouponMutation();

  const onSelectType = (type: COUPON_ENG_TYPE) => {
    setType(type);
  };

  const onSelectModifier = (modifier: COUPON_MODIFIERS) => {
    setModifier(modifier);
  };

  const onSelectColor = (color: COUPON_COLORS) => {
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

    createCouponMutate.mutate(
      {
        receivers: receiverList.map(({ id }) => id),
        backgroundColor: color,
        modifier,
        message,
        couponType: type,
      },
      {
        onSuccess() {
          navigate(PATH.LANDING, {
            state: {
              action: 'create',
            },
          });
        },
      }
    );
  };

  return {
    state: {
      receiverList,
      couponType: type,
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
