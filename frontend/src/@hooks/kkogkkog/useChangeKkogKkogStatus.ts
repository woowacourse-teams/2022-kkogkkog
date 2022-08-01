import { AxiosResponse } from 'axios';
import { UseMutationOptions } from 'react-query';

import { ChangeKkogKkogStatusRequest } from '@/types/remote/request';

import { useChangeKkogKkogStatusMutation } from '../@queries/kkogkkog';

type ChangeStatusMutationOptions = Omit<
  UseMutationOptions<
    AxiosResponse<any, any>,
    unknown,
    { id: number; body: ChangeKkogKkogStatusRequest }
  >,
  'mutationFn'
>;

const useChangeKkogKkogStatus = (id: number) => {
  const changeStatusMutate = useChangeKkogKkogStatusMutation();

  const cancelKkogKkog = (options: ChangeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL' } }, options);
  };

  const requestKkogKKog = (
    { meetingDate }: { meetingDate: string },
    options: ChangeStatusMutationOptions
  ) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', meetingDate } }, options);
  };

  const finishKkogKkog = (options: ChangeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH' } }, options);
  };

  const acceptKkogKkog = (options: ChangeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'ACCEPT' } }, options);
  };

  return {
    cancelKkogKkog,
    requestKkogKKog,
    finishKkogKkog,
    acceptKkogKkog,
  };
};

export default useChangeKkogKkogStatus;
