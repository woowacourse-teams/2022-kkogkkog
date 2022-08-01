import { AxiosResponse } from 'axios';
import { UseMutationOptions } from 'react-query';

import { ChangeKkogKkogStatusRequest } from '@/types/remote/request';

import { useKkogKkogStatusMutation } from '../@queries/kkogkkog';

type kkogkkogActionType = {
  id: number;
  meetingDate?: string;
};

type changeStatusMutationOptions = Omit<
  UseMutationOptions<
    AxiosResponse<any, any>,
    unknown,
    { id: number; body: ChangeKkogKkogStatusRequest }
  >,
  'mutationFn'
>;

const useChangeKkogKkogStatus = () => {
  const changeStatusMutate = useKkogKkogStatusMutation();

  const cancelKkogKkog = ({ id }: kkogkkogActionType, options: changeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL' } }, options);
  };

  const requestKkogKKog = (
    { id, meetingDate }: kkogkkogActionType,
    options: changeStatusMutationOptions
  ) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', meetingDate } }, options);
  };

  const finishKkogKkog = ({ id }: kkogkkogActionType, options: changeStatusMutationOptions) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH' } }, options);
  };

  const acceptKkogKkog = ({ id }: kkogkkogActionType, options: changeStatusMutationOptions) => {
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
