import { useQuery } from 'react-query';

import { getKkogkkogList } from '@/apis/kkogkkog';

export const useKkogKkogList = () =>
  useQuery(['kkogkkogList'], getKkogkkogList, {
    suspense: true,
    select(data) {
      return data.data;
    },
  });
