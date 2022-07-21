import { useQuery } from 'react-query';

import { getKkogkkogList } from '@/apis/kkogkkog';
import { KkogKkogListResponse } from '@/types/remote/response';

export const useKkogKkogList = () => {
  const kkogkkogListQuery = useQuery<{ data: KkogKkogListResponse }>(
    ['kkogkkogList'],
    getKkogkkogList,
    {
      suspense: false,
    }
  );

  return {
    kkogkkogList: kkogkkogListQuery.data?.data,
    ...kkogkkogListQuery,
  };
};
