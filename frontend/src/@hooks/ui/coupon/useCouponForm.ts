import { FormEventHandler, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useCreateCoupon } from '@/@hooks/business/coupon';
import { DYNAMIC_PATH, PATH } from '@/Router';
import {
  COUPON_COLORS,
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponColors,
  couponHashtags,
  couponTypeCollection,
} from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';
import { isOverMaxLength } from '@/utils/validations';

export const useCouponForm = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const [receiverList, setReceiverList] = useState<UserResponse[]>([]);
  const [type, setType] = useState<COUPON_ENG_TYPE>(couponTypeCollection[0].engType);
  const [hashtag, setHashtag] = useState<COUPON_HASHTAGS>(couponHashtags[0]);
  const [color, setColor] = useState<COUPON_COLORS>(couponColors[0]);

  const [description, onChangeDescription] = useInput('', [
    (value: string) => isOverMaxLength(value, 50),
  ]);

  const { createCoupon } = useCreateCoupon();

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

  const onSubmitCreateForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    if (!window.confirm('쿠폰을 생성하시겠습니까?')) {
      return;
    }

    if (receiverList.length === 0) {
      displayMessage('받을 사람을 선택해주세요', true);

      return;
    }

    if (description.length > 50) {
      displayMessage('50자 이내로 작성해주세요', true);

      return;
    }

    const coupons = await createCoupon({
      receiverList,
      hashtag,
      description,
      type,
    });

    if (coupons.length === 1) {
      navigate(DYNAMIC_PATH.COUPON_DETAIL(coupons[0].id), { replace: true });

      return;
    }

    navigate(PATH.MAIN);
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
