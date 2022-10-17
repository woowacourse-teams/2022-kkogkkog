import { FormEventHandler, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useCreateUnregisteredCoupon } from '@/@hooks/business/unregistered-coupon';
import { DYNAMIC_PATH, PATH } from '@/Router';
import {
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponHashtags,
  couponTypeCollection,
} from '@/types/coupon/client';
import { isOverMaxLength } from '@/utils/validations';

export const useUnregisteredForm = () => {
  const navigate = useNavigate();

  const { displayMessage } = useToast();

  const [couponCount, setCouponCount] = useState(1);
  const [couponType, setCouponType] = useState<COUPON_ENG_TYPE>(couponTypeCollection[0].engType);
  const [couponTag, setCouponTag] = useState<COUPON_HASHTAGS>(couponHashtags[0]);
  const [couponMessage, onChangeCouponMessage] = useInput('', [
    (value: string) => isOverMaxLength(value, 50),
  ]);

  const { createUnregisteredCoupon } = useCreateUnregisteredCoupon();

  const onClickCouponCountUpdateButton = (count: number) => () => {
    if (isNaN(count)) {
      return;
    }

    if (count > 5) {
      displayMessage('쿠폰의 수는 5개를 초과할 수 없어요', true);

      return;
    }

    if (count < 1) {
      displayMessage('쿠폰의 수는 1개보다 적을 수 없어요', true);

      return;
    }

    setCouponCount(count);
  };

  const onSelectCouponType = (type: COUPON_ENG_TYPE) => () => {
    setCouponType(type);
  };

  const onSelectCouponTag = (couponTag: COUPON_HASHTAGS) => () => {
    setCouponTag(couponTag);
  };

  const onSubmitUnregisteredCouponCreateForm: FormEventHandler<HTMLFormElement> = async e => {
    e.preventDefault();

    if (!window.confirm('쿠폰을 생성하시겠습니까?')) {
      return;
    }

    if (couponMessage.length > 50) {
      displayMessage('50자 이내로 작성해주세요', true);

      return;
    }

    const unregisteredCouponList = await createUnregisteredCoupon({
      quantity: couponCount,
      couponTag,
      couponMessage,
      couponType,
    });

    if (unregisteredCouponList.length === 1) {
      navigate(DYNAMIC_PATH.UNREGISTERED_COUPON_DETAIL(unregisteredCouponList[0].id), {
        replace: true,
      });
    }

    navigate(PATH.UNREGISTERED_COUPON_LIST);
  };

  return {
    state: {
      couponCount,
      couponType,
      couponTag,
      couponMessage,
    },
    handler: {
      onClickCouponCountUpdateButton,
      onSelectCouponType,
      onSelectCouponTag,
      onChangeCouponMessage,
      onSubmitUnregisteredCouponCreateForm,
    },
  };
};
