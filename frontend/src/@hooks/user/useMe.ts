import { useQuery } from 'react-query';

import { getMe } from '@/apis/user';
import { MeResponse } from '@/types/remote/response';

const useMe = () => {
  const { data, isLoading, isError, remove } = useQuery<{ data: MeResponse }>(['me'], getMe, {
    onError: error => {
      console.log(error);
    },
    suspense: false,
    staleTime: 6000,
  });

  return {
    me: data?.data,
    isLoading,
    isError,
    remove,
  };
};

export default useMe;
