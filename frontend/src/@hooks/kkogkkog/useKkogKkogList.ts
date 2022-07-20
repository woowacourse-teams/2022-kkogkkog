import { useQuery } from 'react-query';

import { getKkogkkogList } from '@/apis/kkogkkog';
import { KkogKkogListResponse } from '@/types/remote/response';

export const useKkogKkogList = () => {
  const kkogkkogListQuery = useQuery<{ data: KkogKkogListResponse }>(
    ['kkogkkogList'],
    getKkogkkogList
  );

  return {
    kkogkkogList: kkogkkogListQuery.data?.data,
    ...kkogkkogListQuery,
  };
};
