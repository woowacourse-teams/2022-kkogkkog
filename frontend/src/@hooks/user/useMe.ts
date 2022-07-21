import { useQuery } from 'react-query';

import { getMe } from '@/apis/user';
import { MeResponse } from '@/types/remote/response';

const useMe = () => {
  const meQuery = useQuery<{ data: MeResponse }>(['me'], getMe, {
    suspense: true,
  });

  return {
    me: meQuery.data?.data,
    ...meQuery,
  };
};

export default useMe;
