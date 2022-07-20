import { useQuery } from 'react-query';

import { getKkogkkogList } from '@/apis/kkogkkog';
import { KkogKkogListResponse } from '@/types/remote/response';

export const useKkogKkogList = () => {
  const { data, isLoading, isError, remove } = useQuery<{ data: KkogKkogListResponse }>(
    ['kkogkkogList'],
    getKkogkkogList
  );

  return {
    kkogkkogList: data?.data,
    isLoading,
    isError,
    remove,
  };
};
