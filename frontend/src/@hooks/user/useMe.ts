import { useQuery } from 'react-query';

import { getMe } from '@/apis/user';

const useMe = () =>
  useQuery(['me'], getMe, {
    suspense: false,
    refetchOnWindowFocus: false,
    select(data) {
      return data.data;
    },
  });

export default useMe;
