import { FormEventHandler, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { PATH } from '@/Router';
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

  const onChangeCouponCount = (count: number) => {
    if (isNaN(count)) {
      return;
    }

    if (count < 1 || count > 5) {
      return;
    }

    setCouponCount(count);
  };

  const onSelectCouponType = (type: COUPON_ENG_TYPE) => {
    setCouponType(type);
  };

  const onSelectCouponTag = (couponTag: COUPON_HASHTAGS) => {
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

    // submitAction
    // const coupons = await createCoupon({
    //   receiverIds: receiverList.map(({ id }) => id),
    //   couponTag,
    //   couponMessage,
    //   couponType,
    // });

    if (couponCount === 1) {
      // 미등록 쿠폰 조회 페이지로
      // navigate(DYNAMIC_PATH.COUPON_DETAIL(coupons[0].id), { replace: true });
      navigate(PATH.MAIN);

      return;
    }

    navigate(PATH.MAIN);
  };

  return {
    state: {
      couponCount,
      couponType,
      couponTag,
      couponMessage,
    },
    changeHandler: {
      onChangeCouponCount,
      onSelectCouponType,
      onSelectCouponTag,
      onChangeCouponMessage,
    },
    submitHandler: {
      create: onSubmitUnregisteredCouponCreateForm,
    },
  };
};
