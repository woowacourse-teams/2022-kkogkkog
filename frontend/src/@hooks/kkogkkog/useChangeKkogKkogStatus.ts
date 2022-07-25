import { useMutation, useQueryClient } from 'react-query';

import { changeKkogkkogStatus } from '@/apis/kkogkkog';

export const useChangeKkogKkogStatus = () => {
  const queryClient = useQueryClient();

  return useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries('kkogkkogList');
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });
};
