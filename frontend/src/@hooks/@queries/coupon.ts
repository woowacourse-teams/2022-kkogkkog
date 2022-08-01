import { useMutation, useQuery, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { changeCouponStatus, createCoupon, getCouponList } from '@/apis/coupon';
import { PATH } from '@/Router';

const QUERY_KEY = {
  couponList: 'couponList',
};

export const useFetchCouponList = () =>
  useQuery([QUERY_KEY.couponList], getCouponList, {
    suspense: true,
    select(data) {
      return data.data;
    },
  });

export const useCreateCouponMutation = () => {
  const navigate = useNavigate();

  return useMutation(createCoupon, {
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
};

export const useChangeCouponStatusMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(changeCouponStatus, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.couponList);
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });
};
