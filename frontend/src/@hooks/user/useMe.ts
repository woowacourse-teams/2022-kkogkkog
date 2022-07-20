import { useQuery } from 'react-query';

import { getMe } from '@/apis/user';
import { MeResponse } from '@/types/remote/response';

const useMe = () => {
  const meQuery = useQuery<{ data: MeResponse }>(['me'], getMe, {
    suspense: false,
  });

  return {
    ...meQuery,
    me: meQuery.data?.data,
  };
};

export default useMe;
