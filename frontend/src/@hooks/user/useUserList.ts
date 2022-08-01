import { useQuery } from 'react-query';

import { getUserList } from '@/apis/user';

const useUserList = () => {
  return useQuery(['getUserList'], getUserList, {
    suspense: false,
    select(data) {
      return data.data;
    },
  });
};

export default useUserList;
