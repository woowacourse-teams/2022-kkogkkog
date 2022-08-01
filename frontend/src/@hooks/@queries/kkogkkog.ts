import { useMutation, useQuery, useQueryClient } from 'react-query';

import { changeKkogkkogStatus, getKkogkkogList } from '@/apis/kkogkkog';

const QUERY_KEY = {
  kkogkkogList: 'kkogkkogList',
};

export const useFetchKkogKkogList = () =>
  useQuery([QUERY_KEY.kkogkkogList], getKkogkkogList, {
    suspense: true,
    select(data) {
      return data.data;
    },
  });

export const useKkogKkogStatusMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.kkogkkogList);
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });
};
