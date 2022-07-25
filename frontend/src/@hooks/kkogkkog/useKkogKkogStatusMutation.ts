import { useMutation, useQueryClient } from 'react-query';

import { changeKkogkkogStatus } from '@/apis/kkogkkog';

type kkogkkogActionType = {
  id: number;
  message: string;
};

const useKkogKkogStatusMutation = () => {
  const queryClient = useQueryClient();

  const changeStatusMutate = useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
    },
  });

  const cancelKkogKkog = ({ id, message }: kkogkkogActionType) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'CANCEL', message } });
  };

  const requestKkogKKog = ({ id, message }: kkogkkogActionType) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'REQUEST', message } });
  };

  const finishKkogKkog = ({ id, message }: kkogkkogActionType) => {
    // changeStatusMutate.mutate({ id, body: { couponEvent: 'FINISH', message } });
  };

  const acceptKkogKkog = ({ id, message }: kkogkkogActionType) => {
    changeStatusMutate.mutate({ id, body: { couponEvent: 'ACCEPT', message } });
  };

  return {
    cancelKkogKkog,
    requestKkogKKog,
    finishKkogKkog,
    acceptKkogKkog,
  };
};

export default useKkogKkogStatusMutation;
