import { AxiosResponse } from 'axios';
import { UseMutationOptions } from 'react-query';

import { ChangeCouponStatusRequest } from '@/types/remote/request';

import { useChangeCouponStatusMutation } from '../@queries/coupon';

type ChangeStatusMutationOptions = Omit<
  UseMutationOptions<
    AxiosResponse<any, any>,
    unknown,
    { id: number; body: ChangeCouponStatusRequest }
  >,
  'mutationFn'
>;

const useChangeCouponStatus = (id: number) => {
  const changeStatusMutate = useChangeCouponStatusMutation();

  const cancelCoupon = (options: ChangeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL' } }, options);
  };

  const requestCoupon = (
    { meetingDate }: { meetingDate: string },
    options: ChangeStatusMutationOptions
  ) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', meetingDate } }, options);
  };

  const finishCoupon = (options: ChangeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH' } }, options);
  };

  const acceptCoupon = (options: ChangeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'ACCEPT' } }, options);
  };

  return {
    cancelCoupon,
    requestCoupon,
    finishCoupon,
    acceptCoupon,
  };
};

export default useChangeCouponStatus;
