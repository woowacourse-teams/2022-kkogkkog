import { AxiosError } from 'axios';
import { FormEventHandler, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { PATH } from '@/Router';
import {
  COUPON_COLORS,
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponColors,
  couponHashtags,
  couponTypeCollection,
} from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';

import { useCreateCouponMutation } from '../@queries/coupon';

export const useCouponForm = () => {
  const { displayMessage } = useToast();

  const navigate = useNavigate();

  const [receiverList, setReceiverList] = useState<UserResponse[]>([]);
  const [type, setType] = useState<COUPON_ENG_TYPE>(couponTypeCollection[0].engType);
  const [hashtag, setHashtag] = useState<COUPON_HASHTAGS>(couponHashtags[0]);
  const [color, setColor] = useState<COUPON_COLORS>(couponColors[0]);

  const [description, onChangeDescription] = useInput('');

  const createCouponMutate = useCreateCouponMutation();

  const onSelectType = (type: COUPON_ENG_TYPE) => {
    setType(type);
  };

  const onSelectHashtag = (hashtag: COUPON_HASHTAGS) => {
    setHashtag(hashtag);
  };

  const onSelectColor = (color: COUPON_COLORS) => {
    setColor(color);
  };

  const onSelectReceiver = (user: UserResponse) => {
    const isSelected = receiverList.some(receiver => receiver.id === user.id);

    if (isSelected) {
      setReceiverList(prev => prev.filter(({ id: receiverId }) => receiverId !== user.id));

      return;
    }

    setReceiverList(prev => [...prev, user]);
  };

  const onSubmitCreateForm: FormEventHandler<HTMLFormElement> = e => {
    e.preventDefault();

    if (receiverList.length === 0) {
      displayMessage('받을 사람을 선택해주세요', true);

      return;
    }

    if (description.length > 50) {
      displayMessage('50자 이내로 작성해주세요', true);

      return;
    }

    createCouponMutate.mutate(
      {
        receiverIds: receiverList.map(({ id }) => id),
        hashtag,
        description,
        couponType: type,
      },
      {
        onSuccess() {
          displayMessage('쿠폰을 생성했어요', false);

          navigate(PATH.LANDING, {
            state: {
              action: 'create',
            },
          });
        },
        onError(error) {
          if (error instanceof AxiosError) {
            displayMessage(error?.response?.data?.message, true);
          }
        },
      }
    );
  };

  return {
    state: {
      receiverList,
      couponType: type,
      hashtag,
      color,
      description,
    },
    changeHandler: {
      onSelectReceiver,
      onSelectType,
      onSelectHashtag,
      onSelectColor,
      onChangeDescription,
    },
    submitHandler: {
      create: onSubmitCreateForm,
    },
  };
};
