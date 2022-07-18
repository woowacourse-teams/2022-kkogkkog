import { useQuery } from 'react-query';

import { getMe } from '@/apis/user';
import { User } from '@/types/domain';

const useMe = () => {
  const { data, isLoading, isError, remove } = useQuery<{ data: User }>(['me'], getMe, {
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
