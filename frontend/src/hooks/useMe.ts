import { useQuery } from 'react-query';

import { getMe } from '@/apis/user';

const useMe = () => {
  const { data, remove, refetch } = useQuery(['me'], getMe, {
    onError: error => {
      console.log(error);
    },
    suspense: false,
    staleTime: 6000,
  });

  return {
    me: data?.data?.data,
    remove,
  };
};

export default useMe;
