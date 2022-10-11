import { FormEventHandler, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useCreateCoupon } from '@/@hooks/business/coupon';
import { DYNAMIC_PATH, PATH } from '@/Router';
import {
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponHashtags,
  couponTypeCollection,
} from '@/types/coupon/client';
import { UserResponse } from '@/types/user/remote';
import { isOverMaxLength } from '@/utils/validations';

export const useCouponForm = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const [receiverList, setReceiverList] = useState<UserResponse[]>([]);
  const [couponType, setCouponType] = useState<COUPON_ENG_TYPE>(couponTypeCollection[0].engType);
  const [couponTag, setCouponTag] = useState<COUPON_HASHTAGS>(couponHashtags[0]);
  const [couponMessage, onChangeCouponMessage] = useInput('', [
    (value: string) => isOverMaxLength(value, 50),
  ]);

  const { createCoupon } = useCreateCoupon();

  const onSelectCouponType = (type: COUPON_ENG_TYPE) => () => {
    setCouponType(type);
  };

  const onSelectCouponTag = (couponTag: COUPON_HASHTAGS) => () => {
    setCouponTag(couponTag);
  };

  const onSelectReceiver = (user: UserResponse) => () => {
    const isSelected = receiverList.some(receiver => receiver.id === user.id);

    if (isSelected) {
      setReceiverList(prev => prev.filter(({ id: receiverId }) => receiverId !== user.id));

      return;
    }

    setReceiverList(prev => [...prev, user]);
  };

  const onSubmitCouponCreateForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    if (!window.confirm('쿠폰을 생성하시겠습니까?')) {
      return;
    }

    if (receiverList.length === 0) {
      displayMessage('받을 사람을 선택해주세요', true);

      return;
    }

    if (couponMessage.length > 50) {
      displayMessage('50자 이내로 작성해주세요', true);

      return;
    }

    const coupons = await createCoupon({
      receiverIds: receiverList.map(({ id }) => id),
      couponTag,
      couponMessage,
      couponType,
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
      couponType,
      couponTag,
      couponMessage,
    },
    handler: {
      onSelectReceiver,
      onSelectCouponType,
      onSelectCouponTag,
      onChangeCouponMessage,
      onSubmitCouponCreateForm,
    },
  };
};
