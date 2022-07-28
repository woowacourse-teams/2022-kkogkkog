import { useMutation, UseMutationOptions, useQueryClient } from 'react-query';

import { changeKkogkkogStatus } from '@/apis/kkogkkog';

type kkogkkogActionType = {
  id: number;
  meetingDate?: string;
};

const useKkogKkogStatusMutation = () => {
  const queryClient = useQueryClient();

  const changeStatusMutate = useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
    },
  });

  const cancelKkogKkog = (
    { id }: kkogkkogActionType,
    options: Omit<UseMutationOptions<unknown, unknown, unknown>, 'mutationFn'>
  ) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL' } }, options);
  };

  const requestKkogKKog = (
    { id, meetingDate }: kkogkkogActionType,
    options: Omit<UseMutationOptions<unknown, unknown, unknown>, 'mutationFn'>
  ) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', meetingDate } }, options);
  };

  const finishKkogKkog = (
    { id }: kkogkkogActionType,
    options: Omit<UseMutationOptions<unknown, unknown, unknown>, 'mutationFn'>
  ) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH' } }, options);
  };

  const acceptKkogKkog = (
    { id }: kkogkkogActionType,
    options: Omit<UseMutationOptions<unknown, unknown, unknown>, 'mutationFn'>
  ) => {
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
