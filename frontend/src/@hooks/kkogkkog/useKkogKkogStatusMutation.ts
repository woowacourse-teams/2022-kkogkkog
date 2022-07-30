import { AxiosResponse } from 'axios';
import { useMutation, UseMutationOptions, useQueryClient } from 'react-query';

import { changeKkogkkogStatus } from '@/apis/kkogkkog';
import { ChangeKkogKkogStatusRequest } from '@/types/remote/request';

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

const useKkogKkogStatusMutation = () => {
  const queryClient = useQueryClient();

  const changeStatusMutate = useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
    },
  });

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

export default useKkogKkogStatusMutation;
